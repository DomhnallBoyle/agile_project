package uk.ac.qub.csc3045.api.utility;

import java.util.Properties;    
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import uk.ac.qub.csc3045.api.security.SecurityConstants;


public class EmailUtility {

	public EmailUtility() {
		
	}
	
	public void sendEmail(String toAddress, String subject, String body) {
		final String username = SecurityConstants.SERVER_USERNAME;
		final String password = SecurityConstants.SERVER_PASSWORD;

		// Setting up the Properties for SMTP Client
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// Checking if Our Email Service address has got the correct Uname,Password
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("csc3045cs2test@gmail.com"));
			// recipients email address
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
			// add the Subject of email
			message.setSubject(subject);
			// message body
			message.setText(body);
			//Return Email Sent Message to Backend 
			Transport.send(message);
			System.out.println("Email Sent Successfully");

		} catch (MessagingException e) {
			throw new RuntimeException(e);

		}
	}
	
	/*
	 * Sample Usage 
	 *  EmailUtility email = new EmailUtility();
		email.sendEmail("ciaran.duncan@gmail.com", "You are Registered", "Hi"+account.getUsername()+
		"You have been registered");
	 */



}