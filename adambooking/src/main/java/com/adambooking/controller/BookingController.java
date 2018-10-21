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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.adambooking.aggregator.BookingAggregator;
import com.adambooking.email.EmailClient;
import com.adambooking.email.EmailConverter;
import com.adambooking.model.AdminBooking;
import com.adambooking.model.Booking;
import com.adambooking.model.ContactUs;
import com.adambooking.model.EditorRequest;
import com.adambooking.model.EditorResponse;
import com.adambooking.model.Email;
import com.adambooking.model.TimeSlot;

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
		String insertStatus = "{}";
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
		System.out.println("action: " + editorRequest.getAction());
		Booking booking = new Booking();
		Map<Integer, Booking> editBookingMap = new HashMap();
		editBookingMap = editorRequest.getData();
		
		Set<Integer> idSet = editBookingMap.keySet();
		Object[] idArray = idSet.toArray();
		//booking = editBookingMap.get(140);
		Integer id = (Integer) idArray[0];
		booking = editBookingMap.get(id);
		System.out.println("service: " + booking.getService());
		BookingAggregator bookingAggregator = new BookingAggregator();
	
		try{
			bookingAggregator.editBooking(booking);
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			//return "failed";
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
		//System.out.println("service: " + booking.getService());
		//BookingAggregator bookingAggregator = new BookingAggregator();
		String emailStatus = null;
		try{
			//bookingAggregator.saveBooking(booking);
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
		
		//List<AdminBooking> lAdminBooking = new ArrayList();
		List<Booking> lBooking = new ArrayList<Booking>();
		Map<String, List<Booking>> adminBookingMap = new HashMap<String, List<Booking>>();
		
		try{
			
			/*{
		    "DT_RowId": "row_1",
		    "first_name": "Tiger",
		    "last_name": "Nixon",
		    "position": "System Architect",
		    "email": "t.nixon@datatables.net",
		    "office": "Edinburgh",
		    "extn": "5421",
		    "age": "61",
		    "salary": "320800",
		    "start_date": "2011-04-25"
		  },*/
			
			/*
			AdminBooking adminBooking1 = new AdminBooking();
			adminBooking1.setDT_RowId("row_1");
			adminBooking1.setFirst_name("Tiger");
			adminBooking1.setLast_name("Nixon");
			adminBooking1.setPosition("System Architect");
			adminBooking1.setEmail("t.nixon@datatables.net");
			adminBooking1.setOffice("Edinburgh");
			adminBooking1.setExtn("5421");
			adminBooking1.setAge("61");
			adminBooking1.setSalary("320800");
			adminBooking1.setStart_date("2011-04-25");
			lAdminBooking.add(adminBooking1); 
			*/
			
			/* {
				   "id": 140,
				   "service": "beard",
				   "barber": "moe",
				   "firstName": "Hani",
				   "lastName": "Assaad",
				   "phone": "8174580422",
				   "email": "haniassaad@hotmail.com",
				   "startTime": "2018-10-13T11:00:00",
				   "endTime": "2018-10-13T11:30:00"
				}
				*/
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
