package student.examples.businessuservice.services;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import student.examples.businessuservice.util.EmailType;

public class EmailSender {
	
	final String senderEmail = "maks.demianov@gmail.com";
    final String senderPassword = "balw ahei nalz pnma";
    
    private Properties properties;
    private Session session;
    
    public EmailSender() {
    	setup();
    }
    
    private void setup() {
    	// Set up mail server properties
		properties = new Properties();
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    properties.put("mail.smtp.port", "587");
	    
	    session = Session.getInstance(properties, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	          return new PasswordAuthentication(senderEmail, senderPassword);
	        }
	      });
    }
    
	
	public void send(String recipientEmail, String token, EmailType emailType) {
		String confirmationLink = "";
		String htmlContent = "";
		
	    try {
		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(senderEmail));
		    message.setRecipients(
		      Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
		    message.setSubject("Confirmation Email");

            switch (emailType) {
            case REGISTRATION: 
            	confirmationLink = "https://localhost:8444/auth/confirm-signup?token=" + token;
                
                htmlContent = "<h1>Welcome to Godzilla Game!</h1>" +
                        "<p>Thank you for registering. Click the link below to confirm your registration:</p>" +
                        "<a href=\"" + confirmationLink + "\">Confirm Registration</a>";
                break;
                
            case WITHDRAW:
            	confirmationLink = "https://localhost:8444/auth/confirm-withdraw?token=" + token;
                
                htmlContent = "<h1>Godzilla Game</h1>" +
                        "<p>Click the link below to confirm your withdraw:</p>" +
                        "<a href=\"" + confirmationLink + "\">Withdraw</a>";
                break;
            	
            }

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, "text/html; charset=utf-8");
		
		    Multipart multipart = new MimeMultipart();
		    multipart.addBodyPart(mimeBodyPart);
		
		    message.setContent(multipart);
		
		    Transport.send(message);
		    System.out.println("Email sent successfully.");
		    
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
    
	}
}
