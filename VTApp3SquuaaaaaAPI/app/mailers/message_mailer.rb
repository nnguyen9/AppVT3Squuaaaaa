class MessageMailer < ApplicationMailer
	def messageParticipant(bill, user, part)

		# SMSEasy will use actionmailer's default configuration, which can be overriden if needed:
		ActionMailer::Base.smtp_settings = {
		  :address        => 'smtp.gmail.com',
		  :port           => '587',
		  :domain         => 'gmail.com',
		  :user_name	  	=> ENV['send_email'],
		  :password		  	=> ENV['send_email_pass'],
		  :authentication => 'plain',
		  :enable_starttls_auto => true
		}

		# Configure SMSEasy's "from" address:
		SMSEasy::Client.config['from_address'] = "VTApp3Squuaaaa"

		# Optionally override the carries list using your own data file.
		# SMSEasy::Client.configure(YAML.load(...))

		@body = "$#{bill['price']} has been transferred to #{user.first_name} for: #{bill['description']}!"

		# Create the client
		easy = SMSEasy::Client.new

		# Deliver a simple message.
			mail(to: "newyearbaby96@gmail.com", 
					 body: @body, 
					 content_type: "text/html", 
					 subject: "Transfer made!")
			easy.deliver(part.phone, 'at&t', @body)
		# To set a custom from e-mail per SMS message:
		# easy.deliver("5551234567", "verizon", "Sup.", :from => "bob@test.com")

		# # You can set the maximum length of the SMS message, which is not set by default.  Most phones can only accept 128 characters.  To do this just specify the limit option.

		# easy.deliver("5551234567", "verizon", "Boo-yaa!", :limit => 128)

		# You can retrieve just the formatted address to use in your own mailer.
		# SMSEasy::Client.sms_address("5558675309","at&t") # => "5558675309@txt.att.net"
	end
end
