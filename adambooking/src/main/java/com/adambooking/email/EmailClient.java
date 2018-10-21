package com.adambooking.email;

import java.util.*;  

import javax.mail.*;  
import javax.mail.internet.*; 

import com.adambooking.model.Email;


public class EmailClient {
	
	
	
	public static void SendEmail(Email email){
		
	   //Create a Properties object to contain settings for the SMTP protocol provider.
	  // System properties
  	  Properties props = new Properties();  
  	 
  	  // Setup our mail server
  	  props.put("mail.smtp.host", email.getHost());  
  	  //props.put("mail.smtp.user",from); 
  	  props.put("mail.smtp.ssl.enable", "true"); 
  	  props.put("mail.smtp.port", "25"); 
  	  props.put("mail.debug", "true"); 
  	  props.put("mail.smtp.auth", "true"); 
  	  props.put("mail.smtp.starttls.enable","true"); 
  	  props.put("mail.smtp.EnableSSL.enable","true");
  	  props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
  	  props.setProperty("mail.smtp.socketFactory.fallback", "false");   
  	  props.setProperty("mail.smtp.port", "465");   
  	  props.setProperty("mail.smtp.socketFactory.port", "465"); 
  	  
	  //Create a Session instance using the Properties object and the Authenticator object. If SMTP authentication in not needed a null value can be supplied for the Authenticator.
	  //Refer to this page to create the application password
  	  //https://support.google.com/mail/answer/1173270?hl=en
  	  //Generate App Password
  	  //https://accounts.google.com/signin/v2/sl/pwd?service=accountsettings&passive=1209600&osid=1&continue=https%3A%2F%2Fmyaccount.google.com%2Fapppasswords&followup=https%3A%2F%2Fmyaccount.google.com%2Fapppasswords&rart=ANgoxcebn905rq9lqolI8Cz3NyBVgWlurGI9p91w1WCPC9jYKo_cjjTPBHzspCLXWbGsf4HJ53Tfls5XnIJa9LJqPu0y_z6amw&authuser=0&csig=AF-SEnYNP62RTu0TF-2E%3A1528181245&flowName=GlifWebSignIn&flowEntry=ServiceLogin
  	  //Select device and select app and hit generate
  	  Session session = Session.getInstance(props, new GMailAuthenticator("hani.assaad@gmail.com", "cnroneifwlpqwver"));
		session.setDebug(email.isDebug());
		
		//Construct a MimeMessage instance, populate the message headers and content and then send the message.
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
		    //message.setFrom(new InternetAddress(from));
		    InternetAddress[] address = {new InternetAddress(email.getEmailTo())};
		    mimeMessage.setRecipients(Message.RecipientType.TO, address);
		    mimeMessage.setSubject(email.getSubject());
		    mimeMessage.setSentDate(new Date());
		    mimeMessage.setText(email.getMessage());
		    Transport.send(mimeMessage);
		} catch (MessagingException ex) {
		    ex.printStackTrace();
		}
	}
	
	
}
