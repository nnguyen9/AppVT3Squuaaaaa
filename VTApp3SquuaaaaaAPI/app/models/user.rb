require 'http'

class User < ActiveRecord::Base
	# Makes sure all users have the required fields
	validates :first_name, :last_name, :cap_id, :phone, :presence => true

	# Password param used to create encrypted password
	attr_accessor :password

	def add_cap_id(cap_id)
		rawResponse = HTTP.get("http://api.reimaginebanking.com/accounts", :params => {:key => "bf0eebcb460b5b6888a7dfb8aaf85b4e", :type => "Checking"})
		@body = JSON.parse rawResponse.body
		self.cap_id = @body[cap_id]['_id']
	end

	# Encrypts and sets password
	def encrypt_password(password)
		if password != nil
    	self.password_hash = User.createHash(password)
    end
	end
		
	# Authenticates and finds the user with the information
	def self.authenticate(phone, password)
	  @user = User.find_by_phone(phone)
	  if @user && User.validatePassword(password, @user.password_hash)
	  	@user
	  else
	  	nil
	  end
	end
	
#	module Helpers
#    	extend ActionView::Helpers::UsersHelper
#  	end
	
	private
	
	PBKDF2_ITERATIONS = 1000
	  SALT_BYTE_SIZE = 24
	  HASH_BYTE_SIZE = 24

	  HASH_SECTIONS = 4
	  SECTION_DELIMITER = ':'
	  ITERATIONS_INDEX = 1
	  SALT_INDEX = 2
	  HASH_INDEX = 3

	  # Returns a salted PBKDF2 hash of the password.
	  def self.createHash( password )
		salt = SecureRandom.base64( SALT_BYTE_SIZE )
		pbkdf2 = OpenSSL::PKCS5::pbkdf2_hmac_sha1(
		  password,
		  salt,
		  PBKDF2_ITERATIONS,
		  HASH_BYTE_SIZE
		)
		return ["sha1", PBKDF2_ITERATIONS, salt, Base64.encode64( pbkdf2 )].join( SECTION_DELIMITER )
	  end

	  # Checks if a password is correct given a hash of the correct one.
	  # correctHash must be a hash string generated with createHash.
	  def self.validatePassword( password, correctHash )
		params = correctHash.split( SECTION_DELIMITER )
		return false if params.length != HASH_SECTIONS

		pbkdf2 = Base64.decode64( params[HASH_INDEX] )
		testHash = OpenSSL::PKCS5::pbkdf2_hmac_sha1(
		  password,
		  params[SALT_INDEX],
		  params[ITERATIONS_INDEX].to_i,
		  pbkdf2.length
		)

		return pbkdf2 == testHash
	  end
end
