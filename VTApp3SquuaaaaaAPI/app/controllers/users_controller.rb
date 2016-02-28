require 'http'
require 'sms-easy'
require 'net/http'

class UsersController < ApplicationController
	skip_before_action :verify_authenticity_token
	before_action :set_default_response_format

	# Returns all users
	# GET /users
	def index
		@users = User.all
	end

	# Creates a new user and passes to show
	# POST /users
	def create
		@user = User.find_by_phone(params[:phone])
		if @user == nil
			@user = User.new(query_param)
			@user.add_cap_id(params[:cap_id].to_i - 1)

			@user.encrypt_password(params[:password])
			if params[:password] != nil && @user.save
				render action: "show", id: @user.phone
			else
				render :json => {:body => "invalid user"}, :status => 409
			end
		else
			render action: "show", id: @user.phone
		end
	end

	# Finds the user by phone
	# GET /users/{phone}.json
	def show
		@user = User.find_by_phone(params[:id])		
	end

	# Signs and verifies the users information
	# GET /signin OR GET /users/signin
	def signin
		@user = User.authenticate(params[:phone], params[:password])

		if @user
			render action: "show", id: @user.phone
		else
			# Create an error message.
			render :json => {:body => "Invalid phone or password"}, :status => 401
		end
	end

	def destroy
		@user = User.find_by_phone(params[:id])
		@user.destroy
		render :json => {:body => "User deleted"}, :status => 200
	end

	# 'Pays' the bill and requests money from all participants
	# POST users/{phone}/processBills
	def processBills
		@user = User.find_by_phone(params[:id])
		@bills = params[:bills]

		time = Time.new
		@today = time.strftime("%Y-%m-%d")

		headers = {
			"Content-Type" => "application/json"
		}
		if @bills.kind_of?(Array)
			@bills.each do |bill|
				@part = User.find_by_phone(bill['part_phone'])
				body = {
				  "medium" => "balance",
				  "payee_id" => @user.cap_id,
				  "amount" => bill['price'],
				  "transaction_date" => @today,
				  "status" => "pending",
				  "description" => bill['description']
				}.to_json

				url = "http://api.reimaginebanking.com/accounts/#{@part.cap_id}/transfers?key=bf0eebcb460b5b6888a7dfb8aaf85b4e"

				request = Net::HTTP::Post.new(url)
				request.add_field('content-type', 'application/json')
				request.body = @params

				uri = URI.parse(url)
				http = Net::HTTP.new(uri.host, uri.port)
				response = http.request(request)
				@body = JSON.parse(response.body)

				MessageMailer::messageParticipant(bill, @user, @part).deliver_now

				render :json => {:body => "Participants messaged!"}, :status => 200
			end
		else
			@part = User.find_by_phone(@bills['part_phone'])
				@params = {
				  "medium" => "balance",
				  "payee_id" => @user.cap_id,
				  "amount" => @bills['price'].to_f,
				  "transaction_date" => @today,
				  "status" => "pending",
				  "description" => @bills['description']
				}.to_json

				url = "http://api.reimaginebanking.com/accounts/#{@part.cap_id}/transfers?key=bf0eebcb460b5b6888a7dfb8aaf85b4e"

				request = Net::HTTP::Post.new(url)
				request.add_field('content-type', 'application/json')
				request.body = @params

				uri = URI.parse(url)
				http = Net::HTTP.new(uri.host, uri.port)
				response = http.request(request)
				@body = JSON.parse(response.body)

				MessageMailer::messageParticipant(@bills, @user, @part).deliver_now

				render :json => {:body => "Participants messaged!"}, :status => 200
		end
	end

	private 

	# Parameters of a user
	def query_param
		params.permit(:first_name, :last_name, :phone)
	end
end
