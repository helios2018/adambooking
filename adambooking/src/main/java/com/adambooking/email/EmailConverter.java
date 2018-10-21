package com.adambooking.email;

import com.adambooking.model.ContactUs;
import com.adambooking.model.Email;

public class EmailConverter {
	
	public static Email ConvertContactUsFormToEmail(ContactUs contactUs)
	{
		Email email = new Email();
		
		email.setFname(contactUs.getFirstName());
		email.setLname(contactUs.getLastName());
		email.setPhone(contactUs.getPhoneNumber());
		email.setEmailFrom(contactUs.getEmailAddress());
		email.setMessage(contactUs.getMessage());
		//email.setBody();
		
		return email;
		
	}

}
