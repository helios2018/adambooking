package adambooking;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import com.adambooking.model.TimeSlot;
import com.adambooking.utilities.CalendarUtility;

public class CalendarUtilityTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testStoreTimeMapping(){
		CalendarUtility calendarUtility = new CalendarUtility();
		try{
			calendarUtility.storeTimeMapping();
		}
		catch(Exception ex){
			System.out.print(ex.getMessage());
		}
	}
	
	@Test
	public void testGetAllTimeSlots(){
		CalendarUtility calendarUtility = new CalendarUtility();
		try{
			List<TimeSlot> lAllTimeSlots = calendarUtility.getAllTimeSlots(30);
		}
		catch(Exception ex){
			System.out.print(ex.getMessage());
		}
	}
	
	@Test
	public void testGetUnAvailableTimeSlots(){
		CalendarUtility calendarUtility = new CalendarUtility();
		try{
			List<TimeSlot> lUnavailableTimeSlots = calendarUtility.getUnAvailableTimeSlots("adam");
			System.out.println(lUnavailableTimeSlots.get(0));
		}
		catch(Exception ex){
			System.out.print(ex.getMessage());
		}
	}
	
	@Test
	public void testGetNextDate(){
		String currentDate = "2018-05-20";
		CalendarUtility calendarUtility = new CalendarUtility();
		try{
			String nextDate = calendarUtility.getNextDate(currentDate, 0);
			System.out.println(nextDate);
		}
		catch(Exception ex){
			System.out.print(ex.getMessage());
		}
	}
	
	@Test
	public void testGetTitle() throws Exception{
		
		CalendarUtility calendarUtility = new CalendarUtility();
		String endTime = calendarUtility.getTitle("10:00:00");
		System.out.println("endTime: " + endTime);
	}
	
	@Test
	public void testConvertDataTableToIsoDateFormat(){
		String dataTableDate = "10-21-2018 2:00 PM";
		CalendarUtility calendarUtility = new CalendarUtility();
		String isoDate = calendarUtility.convertDataTableToIsoDateFormat(dataTableDate);
		System.out.println("isoDate: " + isoDate);
	}
	
	@Test
	public void testGetDateFromIsoDate(){
		String isoDate = "2018-10-21T14:00:00";
		CalendarUtility calendarUtility = new CalendarUtility();
		String date = calendarUtility.getDateFromIsoDate(isoDate);
		System.out.print("date: " + date);
	}
	
	@Test
	public void testGetTimeFromIsoDate(){
		String isoDate = "2018-10-21T14:00:00";
		CalendarUtility calendarUtility = new CalendarUtility();
		String time = calendarUtility.getTimeFromIsoDate(isoDate);
		System.out.print("time: " + time);
	}

}
