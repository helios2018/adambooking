package com.adambooking.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.adambooking.constants.QueryConstants;
import com.adambooking.db.connection.DBConnector;
import com.adambooking.model.TimeSlot;

public class CalendarUtility {

	/**
	 * 
	 * @param serviceName
	 * @return lTimeSlot: list of Time Slots
	 */
	public List<TimeSlot> getTimeSlots(String serviceName) {

		List<TimeSlot> lTimeSlot = new ArrayList();

		// 1- Based on the service, we know the time it takes
		// We may need to store all services in the database along with the time
		// Check db for the time

		// 2- 10am to 6pm is 480 minutes
		// Divide 480 by service time
		// That will give us the number of slots
		// To get the exact date, we create a table that maps 0 to 10:00 for
		// example
		// Example: 0-20 min --> 10am --> 10:20am

		// 3- We need to provide a list of time slots (available and
		// unavailable)
		// To find out the availability, we create another table where we store
		// unavailable times
		// Therefore, we pass start time like 10:00am and end time like 10:20am
		// to know if it's unavailable

		// 4- Set available slots color to green and unavailable to red
		// or just show the available slots .... it can be either way

		return lTimeSlot;

	}

	public void storeTimeMapping() throws Exception {

		// Start at 10:00:00 (0,10:00:00),
		// increment by 1 minute (1,10:01:00), etc ...

		DBConnector.openDatabaseConnection();

		int intTotalMin = 0;
		int intMin = 0;
		String stringMin = null;
		int intHour = 10;
		String timeValue = null;
		for (intHour = 10; intHour < 18; intHour++) {
			for (intMin = 0; intMin < 60; intMin++) {
				stringMin = Integer.toString(intMin);
				if (intMin < 10) {
					stringMin = "0" + Integer.toString(intMin);
				}
				timeValue = Integer.toString(intHour) + ":" + stringMin + ":00";

				System.out.println(intTotalMin + "-->" + timeValue);
				String saveTimeMappingQuery = "insert into time_mapping values (";
				saveTimeMappingQuery = saveTimeMappingQuery + "'" + intTotalMin + "'" + "," + "'" + intTotalMin + "'"
						+ "," + "'" + timeValue + "'" + ")";
				DBConnector.executeUpdate(saveTimeMappingQuery);

				intTotalMin++;
			}
		}
		timeValue = "18:00:00";
		System.out.println(intTotalMin + "-->" + timeValue);
		String saveTimeMappingQuery = "insert into time_mapping values (";
		saveTimeMappingQuery = saveTimeMappingQuery + "'" + intTotalMin + "'" + "," + "'" + intTotalMin + "'" + ","
				+ "'" + timeValue + "'" + ")";

		DBConnector.executeUpdate(saveTimeMappingQuery);

		// Close connection now
		DBConnector.closeDatabaseConnection();
	}

	public List<TimeSlot> getAllTimeSlots(int serviceTime) throws Exception {
		List<TimeSlot> lAllTimeSlots = new ArrayList();
		int availableTimeByMinute = 480; // 480 minutes (8hr * 60) for the whole day
		int availableTimeSlotsCount = availableTimeByMinute / serviceTime;// Number of available slots
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = df.format(new Date());
		
		int n = 0;
		int totalDaysNumber = 7;
		
		while(n < totalDaysNumber){
			
			int i = 0;
			int timeSlotStart = 0;
			int timeSlotEnd = 0;
		
			while (i < availableTimeSlotsCount) {
				timeSlotEnd = timeSlotStart + serviceTime;
	
				String timeSlotStartMappedValue = getMappedTime(timeSlotStart); //10:00:00
				String timeSlotStartFormattedMappedValue = getCalendarTimeFormat(timeSlotStartMappedValue, formattedDate); //2018-05-24T10:00:00
				
				String timeSlotEndMappedValue = getMappedTime(timeSlotEnd); //10:00:00
				String timeSlotEndFormattedMappedValue = getCalendarTimeFormat(timeSlotEndMappedValue, formattedDate); //2018-05-24T10:00:00
				
				System.out.println(i + "- " + "start: " + timeSlotStartFormattedMappedValue + " end: " + timeSlotEndFormattedMappedValue);
	
				timeSlotStart = timeSlotStart + serviceTime;
				
				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setTitle(getTitle(timeSlotEndMappedValue));
				timeSlot.setStart(timeSlotStartFormattedMappedValue);
				timeSlot.setEnd(timeSlotEndFormattedMappedValue);
				timeSlot.setRendering("backgroud");
				timeSlot.setBackgroundColor("green");
				timeSlot.setEditable(false);
				timeSlot.setAllDay(false);
	
				lAllTimeSlots.add(timeSlot);
				
				i++;
				
			}//end of while loop
			
			n++;
			df = new SimpleDateFormat("yyyy-MM-dd");
			formattedDate = df.format(new Date());
			formattedDate = getNextDate(formattedDate, n);
			
			int dayOfTheWeek = getDayOfTheWeek(formattedDate);
			if (dayOfTheWeek == 1){
				n++; //if day of the week is Sunday, go to the next day
				df = new SimpleDateFormat("yyyy-MM-dd");
				formattedDate = df.format(new Date());
				formattedDate = getNextDate(formattedDate, n);
			}
			
			System.out.println("**************** \n");
			System.out.println("n: " + n);
			System.out.println("formattedDate: " + formattedDate);
		
		}//end of for loop
		
		

		return lAllTimeSlots;
	}

	public List<TimeSlot> getUnAvailableTimeSlots(String barber) throws Exception {
		List<TimeSlot> lUnavailableTimeSlots = new ArrayList();

		// open connection
		DBConnector.openDatabaseConnection();
		String selectUnavailableSlots = "select * from unavailable_slots where barber = " + "'" + barber + "'";
		ResultSet rs = null;
		String timeSlotStart = "";
		String timeSlotEnd = "";
		rs = DBConnector.executeQuery(selectUnavailableSlots);
		while (rs.next()) {
			timeSlotStart = rs.getString("startTime");
			timeSlotEnd = rs.getString("endTime");
		
			TimeSlot timeSlot = new TimeSlot();
			timeSlot.setStart(timeSlotStart);
			timeSlot.setEnd(timeSlotEnd);
			
			lUnavailableTimeSlots.add(timeSlot);
			
		}
		// Close connection now
		DBConnector.closeDatabaseConnection();

		return lUnavailableTimeSlots;
	}

	public String getNextDate(String curDate, int i) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(curDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, i);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("day: " + day); // the day of the week in numerical format
		return format.format(calendar.getTime());
	}

	private String getMappedTime(int timeSlot) throws Exception {

		// open connection
		DBConnector.openDatabaseConnection();
		String selectTimeMapping = "select timeValue from time_mapping where id = " + "'" + timeSlot + "'";
		ResultSet rs = null;
		String timeSlotMappedValue = "";
		rs = DBConnector.executeQuery(selectTimeMapping);
		while (rs.next()) {
			timeSlotMappedValue = rs.getString("timeValue");
		}
		// Close connection now
		DBConnector.closeDatabaseConnection();		

		return timeSlotMappedValue;

	}
	
	/**
	 * 
	 * @param mappedTime: it's the mapped time we got from time_mapping table. Example: 10:00:00
	 * @return timeSlotMappedValue: it's date plus letter "T" plus time. Example: 2018-05-20T11:10:00
	 */
	private String getCalendarTimeFormat(String mappedTime, String formattedDate){
		
		// start: 10:00:00 end: 11:00:00
	   // append date and create this format: 2018-05-20T11:10:00
	   String timeSlotMappedValue = "";
	   
	   //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	   //String formattedDate = df.format(new Date());
	   
	   timeSlotMappedValue = formattedDate + "T" + mappedTime;
       return timeSlotMappedValue;
		
	}
	
	/**
	 * 
	 * @param timeSlot has this format 10:00:00. We only need 10:00
	 * @return calendar is showing title in addition to start time
	 * therefore, I will store end time in title. This way event will show startTime - endTime on calender event
	 * @throws Exception
	 */
	public String getTitle(String endTime) throws Exception {

		String[] endTimeArray = endTime.split(":");
		String minute = endTimeArray[1];
		
		String hour = endTimeArray[0];
		if (hour.equals("13")){
			hour = "1";
		} else if (hour.equals("14")){
			hour = "2";
		} else if (hour.equals("15")){
			hour = "3";
		} else if (hour.equals("16")){
			hour = "4";
		} else if (hour.equals("17")){
			hour = "5";
		} else if (hour.equals("18")){
			hour = "6";
		}
		
		String endTimeHourMin = hour + ":" + minute;
		return endTimeHourMin;

	}
	
	public int getDayOfTheWeek(String curDate) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(curDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("day: " + day); // the day of the week in numerical format
		return day;
	}
	
	//10-21-2018 2:00 PM
	//2018-10-21T14:00:00
	public String convertDataTableToIsoDateFormat(String dataTableDate){
		String isoDateFormat = "";
		String time = "";
		String pmAm = "";
		String year = "";
		String month = "";
		String day = "";
		
		String[] dataTableDateArray = dataTableDate.split(" ");
		String dateStr = dataTableDateArray[0];
		String timeStr = dataTableDateArray[1];
		pmAm = dataTableDateArray[2];
		
		String[] dateStrArray = dateStr.split("-");
		month = dateStrArray[0];
		day = dateStrArray[1];
		year = dateStrArray[2];
		
		String[] timeStrArray = timeStr.split(":");
		String hours = timeStrArray[0];
		String minutes = timeStrArray[1];
		
		if (pmAm.equals("PM")){
			int hoursInt = Integer.parseInt(hours);
			hoursInt = hoursInt + 12;
			hours = String.valueOf(hoursInt);
		}
		
		isoDateFormat = year + "-" + month + "-" + day + "T"
		+ hours + ":" + minutes + ":" + "00";
		
		
		
		return isoDateFormat;
	}
	
}
