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
	
	

}
