package adambooking;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.adambooking.helper.AdminHelper;
import com.adambooking.model.TimeSlot;

public class AminHelperTest {

	private AdminHelper adminHelper = new AdminHelper();
	
	@Test
	public void testCustomizeAvailableSlotsList() {
		adminHelper.customizeAvailableSlotsList(new ArrayList<TimeSlot>(), 3);
	}

	@Test
	public void testGenerateRandomNumber() {
		adminHelper.generateRandomNumber();
	}

}
