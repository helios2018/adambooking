package com.adambooking.aggregator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.adambooking.constants.QueryConstants.SELECT_FROM_BOOKING;
import static com.adambooking.constants.QueryConstants.SELECT_LAST_ID_FROM_BOOKING;
import static com.adambooking.constants.QueryConstants.SELECT_FROM_UNAVAILABLE_SLOTS;
import com.adambooking.db.connection.DBConnector;
import com.adambooking.email.EmailClient;
import com.adambooking.helper.AdminHelper;
import com.adambooking.model.Booking;
import com.adambooking.model.Email;
import com.adambooking.model.TimeSlot;
import com.adambooking.utilities.CalendarUtility;

public class BookingAggregator {

	private static final String SELECT_LAST_ID_FROM_BOOKING = null;
	private String className = "BookingAggregator";

	public List<Booking> retrieveBookings() throws Exception {
		String methodName = "getBookings";
		System.out.println(className + "-->" + methodName);

		ResultSet rs = null;
		DBConnector.openDatabaseConnection();
		rs = DBConnector.executeQuery(SELECT_FROM_BOOKING);

		// Good Reference on retrieving values from a ResultSet
		// https://www.tutorialspoint.com/jdbc/navigate-result-sets.htm
		
		List<Booking> lBooking = new ArrayList<Booking>();
		
		while (rs.next()){
			Booking booking = new Booking();
			booking.setId(rs.getInt("id"));
			booking.setService(rs.getString("service"));
			booking.setBarber(rs.getString("barber"));
			booking.setFirstName(rs.getString("firstName"));
			booking.setLastName(rs.getString("lastName"));
			booking.setPhone(rs.getString("phone"));
			booking.setEmail(rs.getString("email"));
			booking.setStartTime(rs.getString("startTime"));
			booking.setEndTime(rs.getString("endTime"));
			lBooking.add(booking);
		}

		// rs.next(); //move to next row
		// System.out.print("id: " + rs.getInt("id"));
		// System.out.println(" note: " + rs.getString("note"));

		/*
		 * int i = 1; while (rs.next()){ System.out.println(rs.getString(i) +
		 * "  "); i++; }
		 */

		DBConnector.closeDatabaseConnection();

		return lBooking;

	}
	
	public boolean isSlotAvailable(Booking booking) throws Exception {
		String methodName = "getBookings";
		System.out.println(className + "-->" + methodName);

		ResultSet rs = null;
		DBConnector.openDatabaseConnection();
		rs = DBConnector.executeQuery(SELECT_FROM_UNAVAILABLE_SLOTS);

		// Good Reference on retrieving values from a ResultSet
		// https://www.tutorialspoint.com/jdbc/navigate-result-sets.htm
		
	  while (rs.next()){
			
			if ( booking.getStartTime().equals(rs.getString("startTime")) &&
					booking.getEndTime().equals(rs.getString("endTime")) ){
				return false;
			}
			
		}
		
		DBConnector.closeDatabaseConnection();

		return true;

	}
	
	public void saveBookingAndSendEmail(Booking booking) throws Exception {
		String methodName = "saveBooking";
		System.out.println(className + "-->" + methodName);
		
		if (this.isSlotAvailable(booking)){
			this.saveBooking(booking);
			this.sendNewBookingEmailNotification(booking);
		}
		else{
			System.out.println("This slot is unavailable!");
		}

	}
	
	public List<TimeSlot> getAvailableTimeSlots(int serviceTime, String barber) throws Exception{
		List<TimeSlot> lAvailableTimeSlots = new ArrayList();
		List<TimeSlot> lAllTimeSlots = new ArrayList();
		List<TimeSlot> lUnavailableTimeSlots = new ArrayList();
		List<TimeSlot> lAvailableTimeSlotsCustomized = new ArrayList();
		
		//lAvailableTimeSlots equals to lAllTimeSlots - lUnavailableTimeSlots
		CalendarUtility calendarUtility = new CalendarUtility();
		lAllTimeSlots = calendarUtility.getAllTimeSlots(serviceTime);
		lUnavailableTimeSlots = calendarUtility.getUnAvailableTimeSlots(barber);
		
		lAllTimeSlots.removeAll(lUnavailableTimeSlots); 
		lAvailableTimeSlots = lAllTimeSlots;
		
		//Show only 3 available slots when you display the calendar
		//Business Requirement
		int availableSlotsCount = 3;
		AdminHelper adminHelper = new AdminHelper();
		lAvailableTimeSlotsCustomized = adminHelper.customizeAvailableSlotsList(lAvailableTimeSlots, availableSlotsCount);
		System.out.println("Available Slots Count: " + lAvailableTimeSlots.size());
		
		return lAvailableTimeSlotsCustomized;
	}
	
	public void saveBooking(Booking booking) throws Exception {
		
		String methodName = "saveBookingInDatabase";
		System.out.println(className + "-->" + methodName);
		
		DBConnector.openDatabaseConnection();
				
		//booking table
		String saveBookingQuery = "insert into booking (service, barber, firstName, lastName, phone, "
				+ "email, startTime, endTime) values (";
		saveBookingQuery = saveBookingQuery 
					+ "'" + booking.getService() + "'" + ","
					+ "'" + booking.getBarber() + "'" + ","
					+ "'" + booking.getFirstName() + "'" + ","
					+ "'" + booking.getLastName() + "'" + ","
					+ "'" + booking.getPhone() + "'" + ","
					+ "'" + booking.getEmail() + "'" + ","
					+ "'" + booking.getStartTime() + "'" + ","
					+ "'" + booking.getEndTime() + "'" + ")";
		DBConnector.executeUpdate(saveBookingQuery);
		
		//Create relationship between booking and unavailable_slots table
		//booking primary key: id
		//unavailable_slots foreign key: bookingId
		//id = bookingId
		
		int lastBookingId = retrieveLastBookingId();
		
		//unavailable_slots table
		String saveSlotsQuery = "insert into unavailable_slots (startTime, endTime, barber, bookingId) "
				+ " values (";
		saveSlotsQuery = saveSlotsQuery 
					+ "'" + booking.getStartTime() + "'" + ","
					+ "'" + booking.getEndTime() + "'" + ","
					+ "'" + booking.getBarber() + "'" + ","
					+ "'" + lastBookingId + "'" + ")";
		DBConnector.executeUpdate(saveSlotsQuery);
		
		DBConnector.closeDatabaseConnection();
	}
	
    public void editBooking(Booking booking) throws Exception {
		
		String methodName = "editBookingInDatabase";
		System.out.println(className + "-->" + methodName);
		
		DBConnector.openDatabaseConnection();
		
		/* (id, service, barber, firstName, lastName, phone, "
				+ "email, startTime, endTime) */
		
		//booking table
		String udpateBookingQuery = "update booking set ";
		udpateBookingQuery = udpateBookingQuery
					+ " service = " + "'" + booking.getService() + "'" + ","
					+ " barber = " + "'" + booking.getBarber() + "'" + ","
					+ " firstName = " + "'" + booking.getFirstName() + "'" + ","
					+ " lastName = " + "'" + booking.getLastName() + "'" + ","
					+ " phone = " + "'" + booking.getPhone() + "'" + ","
					+ " email = " + "'" + booking.getEmail() + "'" + ","
					+ " startTime = " + "'" + booking.getStartTime() + "'" + ","
					+ " endTime = " + "'" + booking.getEndTime() + "'"
					+ " where id = " + booking.getId();
		DBConnector.executeUpdate(udpateBookingQuery);
		
		//unavailable_slots table
		String updateUnavailableSlotsQuery = "update unavailable_slots set ";
		updateUnavailableSlotsQuery = updateUnavailableSlotsQuery
				+ " startTime = " + "'" + booking.getStartTime() + "'" + ","
				+ " endTime = " + "'" + booking.getEndTime() + "'" + ","
				+ " barber = " + "'" + booking.getBarber() + "'"
			    + " where bookingId = " + booking.getId();
		DBConnector.executeUpdate(updateUnavailableSlotsQuery);
		
		DBConnector.closeDatabaseConnection();
	}
    

   public void removeBooking(Booking booking) throws Exception {
	
		String methodName = "removeBooking";
		System.out.println(className + "-->" + methodName);
		
		DBConnector.openDatabaseConnection();
		
		/* (id, service, barber, firstName, lastName, phone, "
				+ "email, startTime, endTime) */
		
		//booking table
		String deleteBookingQuery = "delete from booking ";
		deleteBookingQuery = deleteBookingQuery
					+ " where id = " + booking.getId();
		DBConnector.executeUpdate(deleteBookingQuery);
		
		//unavailable_slots table
		String deleteUnavailableSlotsQuery = "delete from unavailable_slots ";
		deleteUnavailableSlotsQuery = deleteUnavailableSlotsQuery
				+ " where bookingId = " + booking.getId();
		DBConnector.executeUpdate(deleteUnavailableSlotsQuery);
		
		DBConnector.closeDatabaseConnection();
	}

	
	
	public void sendNewBookingEmailNotification(Booking booking){
		
		String methodName = "sendNewBookingEmailNotification";
		System.out.println(className + "-->" + methodName);
		
		Email email = new Email();
		email.setEmailFrom("haniassaad@hotmail.com");
		email.setFname(booking.getFirstName());
		email.setLname(booking.getLastName());
		
		String newLine = ". \n";
		String message = "Service: " + booking.getService() + newLine + 
						 "Barber: " + booking.getBarber() + newLine +
				         "Start Time: " + booking.getStartTime() + newLine +
				         "End Time: " + booking.getEndTime() + newLine + 
				         "First Name: " + booking.getFirstName() + newLine + 
				         "Last Name: " + booking.getLastName() + newLine + 
				         "Phone Number: " + booking.getPhone() + newLine + 
				         "Email Address: " + booking.getEmail() + newLine + newLine + 
				         "This message is from Adam Booking website.";
		
		email.setMessage(message);
		
		EmailClient.SendEmail(email);
	}
	
	public int retrieveLastBookingId() throws Exception {
		String methodName = "retrieveLastBookingId";
		System.out.println(className + "-->" + methodName);

		ResultSet rs = null;
		rs = DBConnector.executeQuery("select max(id) as id from booking");
		rs.next();
		Integer lastBookingId = rs.getInt("id");
		System.out.println("int: " + lastBookingId);

		return lastBookingId;

	}

}
