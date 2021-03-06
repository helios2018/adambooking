package com.adambooking.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.adambooking.aggregator.BookingAggregator;
import com.adambooking.email.EmailClient;
import com.adambooking.email.EmailConverter;
import com.adambooking.model.Booking;
import com.adambooking.model.ContactUs;
import com.adambooking.model.EditorRequest;
import com.adambooking.model.EditorResponse;
import com.adambooking.model.Email;
import com.adambooking.model.TimeSlot;
import com.adambooking.utilities.CalendarUtility;

@Path("bookingController")
public class BookingController {
	
	private String className = "BookingController";
	
	@GET	
	@Path("retrieveBookings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveBookings() {
		String methodName = "getBookings";
		System.out.println(className + "-->" + methodName);
		
		List<Booking> lBooking = new ArrayList<Booking>();
		Map<String, List<Booking>> adminBookingMap = new HashMap<String, List<Booking>>();
		BookingAggregator bookingAggregator = new BookingAggregator();
		try{
			lBooking =  bookingAggregator.retrieveBookings();
			adminBookingMap.put("data", lBooking);
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			return Response.status(500).entity(lBooking).build();
		}
		return Response.status(200).entity(adminBookingMap).build();
		
	}
	
	@GET	
	@Path("retrieveLastBookingId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveLastBookingId() {
		String methodName = "retrieveLastBookingId";
		System.out.println(className + "-->" + methodName);
		int lastBookingId = 0;
		BookingAggregator bookingAggregator = new BookingAggregator();
		try{
			lastBookingId =  bookingAggregator.retrieveLastBookingId();
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			return Response.status(500).entity(lastBookingId).build();
		}
		return Response.status(200).entity(lastBookingId).build();
		
	}
	
	/*
	@GET	
	@Path("saveBooking")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveBooking(Booking booking) {
		String methodName = "saveBooking";
		System.out.println(className + "-->" + methodName);
		String insertStatus = null;
		BookingAggregator bookingAggregator = new BookingAggregator();
		try{
			insertStatus = "{\"msg\":success\"}";
			 bookingAggregator.saveBooking(booking);
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			return Response.status(500).entity(insertStatus).build();
		}
		return Response.status(200).entity(insertStatus).build();
		
	}
	*/
	
	
	@POST	
	@Path("saveBooking")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String saveBooking(Booking booking) {
		String methodName = "saveBooking";
		System.out.println(className + "-->" + methodName);
		System.out.println("service: " + booking.getService());
		BookingAggregator bookingAggregator = new BookingAggregator();
		//String insertStatus = "{}";
		try{
			bookingAggregator.saveBookingAndSendEmail(booking);
			//insertStatus = "{" + "\"" + "success" + "\"" + ":\"" + "success" + "\"" + "}";
			//insertStatus = "{\"msg\":success\"}";
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			return "failed";
			//insertStatus = ex.getMessage();
			//insertStatus = "{\"msg\":failure\"}";
			//return Response.status(500).entity(insertStatus).build();
			//Response.status(500);
		}
		//Response.status(200);
	    //return Response.status(200).entity(insertStatus).build();
		//System.out.println("insertStatus: " + insertStatus);
		//return insertStatus;
		return "ok";
		
	}
	
	
	@POST	
	@Path("editBooking")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editBooking(EditorRequest<Integer, Booking> editorRequest) {
		//{action=edit, data={140={id=140, service=beard, barber=moe, firstName=Hani, lastName=Mani, phone=8174580422, email=haniassaad@hotmail.com, startTime=2018-10-13T11:00:00, endTime=2018-10-13T11:30:00}}}
		String methodName = "editBooking";
		System.out.println(className + "-->" + methodName);
		String action = editorRequest.getAction();
		System.out.println("action: " + action);
		Booking booking = new Booking();
		try{
			//Get Booking from request
			Map<Integer,Booking> editBookingMap = new HashMap<Integer,Booking>();
			editBookingMap = editorRequest.getData();
			Set<Integer> idSet = editBookingMap.keySet();
			Object[] idArray = idSet.toArray();
			
			for (int i=0; i<idArray.length; i++){
				Integer id = (Integer) idArray[i];
				booking = editBookingMap.get(id);
				
				//format edit request date
				CalendarUtility calendarUtility = new CalendarUtility();
				String startTime = null;
				
				if (!booking.getStartTime().contains("T")){
					startTime = calendarUtility.convertDataTableToIsoDateFormat(booking.getStartTime());
				} else{
					startTime = booking.getStartTime();
				}
				String endTime = null;
				if (!booking.getEndTime().contains("T")){
					startTime = calendarUtility.convertDataTableToIsoDateFormat(booking.getEndTime());
				} else{
					startTime = booking.getEndTime();
				}
				booking.setStartTime(startTime);
				booking.setEndTime(endTime);
				
				BookingAggregator bookingAggregator = new BookingAggregator();
				bookingAggregator.editBooking(booking, action);
			}
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			EditorResponse<Booking> response = new EditorResponse<Booking>();
			response.addFieldError("500", "Invalid Time!");
			response.setError("Invalid Time Entry!");
			return Response.status(500).entity(response).build();
		}
		EditorResponse<Booking> response = new EditorResponse<Booking>();
		response.add(booking);
		return Response.status(200).entity(response).build();
		
	}
	
	
	@GET	
	@Path("getAvailableTimeSlots/{serviceTime}/{barber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAvailableTimeSlots(@PathParam("serviceTime") int serviceTime, @PathParam("barber") String barber) {
		String methodName = "getAvailableTimeSlots";
		System.out.println(className + "-->" + methodName);
		
		List<TimeSlot> lTimeSlots = new ArrayList<TimeSlot>();
		BookingAggregator bookingAggregator = new BookingAggregator();
		try{
			lTimeSlots =  bookingAggregator.getAvailableTimeSlots(serviceTime, barber);
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			return Response.status(500).entity(lTimeSlots).build();
		}
		return Response.status(200).entity(lTimeSlots).build();
		
	}
	
	@POST	
	@Path("sendEmail")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendEmail(ContactUs contactUs) {
		String methodName = "sendEmail";
		System.out.println(className + "-->" + methodName);
		String emailStatus = null;
		try{
			Email email = EmailConverter.ConvertContactUsFormToEmail(contactUs);
			EmailClient.SendEmail(email);	
			emailStatus = "Email has been successfully sent!";
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			emailStatus = ex.getMessage();
			return Response.status(500).entity(emailStatus).build();
		}
	    return Response.status(200).entity(emailStatus).build();
	}
	
	@GET	
	@Path("retrieveAdminBookings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveAdminBookings() {
		String methodName = "retrieveAdminBookings";
		System.out.println(className + "-->" + methodName);
		
		List<Booking> lBooking = new ArrayList<Booking>();
		Map<String, List<Booking>> adminBookingMap = new HashMap<String, List<Booking>>();
		
		try{
			
			Booking booking = new Booking();
			booking.setId(140);
			booking.setService("beard");
			booking.setBarber("moe");
			booking.setFirstName("Hani");
			booking.setLastName("Assaad");
			booking.setPhone("8174580422");
			booking.setEmail("haniassaad@hotmail.com");
			booking.setStartTime("2018-10-13T11:00:00");
			booking.setEndTime("2018-10-13T11:30:00");
			lBooking.add(booking); 
			adminBookingMap.put("data", lBooking);
			
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			return Response.status(500).entity(adminBookingMap).build();
		}
		return Response.status(200).entity(adminBookingMap).build();
		
	}
	

}
