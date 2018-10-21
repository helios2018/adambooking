package com.adambooking.model;

public class AdminBooking {
	
	/*
	 *  { data: "id" },
		            { data: "service" },
		            { data: "barber" },
		            { data: "firstName" },
		            { data: "lastName" },
		            { data: "phone" },
		            { data: "email" },
		            { data: "startTime" },
		            { data: "endTime" }
	 */
	
	private int id;
	private String barber;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String startTime;
	private String endTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBarber() {
		return barber;
	}
	public void setBarber(String barber) {
		this.barber = barber;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
