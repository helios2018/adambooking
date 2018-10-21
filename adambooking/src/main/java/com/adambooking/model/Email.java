
package com.adambooking.model;


public class Email {
		
	
	
	//To be grabbed from the user
	private String host = "smtp.gmail.com";	
	private boolean debug = true;
	private String fname;
	private String lname;
	private String phone;
	private String emailFrom;
	private String message;
	private String emailTo = "haniassaad@hotmail.com";
	private String subject = "Email from Adam Booking";
	private String body;
	
	public void setFname(String fname)
	{
		this.fname = fname;
	}
	public String getFname()
	{
		return this.fname;
	}
	
	public void setLname(String lname)
	{
		this.lname = lname;
	}
	public String getLname()
	{
		return this.lname;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getPhone()
	{
		return this.phone;
	}
	
	public void setEmailFrom(String emailFrom)
	{
		this.emailFrom = emailFrom;
	}
	public String getEmailFrom()
	{
		return this.emailFrom;
	}
	
	public void setEmailTo(String emailTo)
	{
		this.emailTo = emailTo;
	}
	public String getEmailTo()
	{
		return this.emailTo;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	public String getMessage()
	{
		return this.message;
	}
	
	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	public String getSubject()
	{
		return this.subject;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
}
