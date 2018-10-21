package adambooking;

import static org.junit.Assert.*;

import org.junit.Test;

import com.adambooking.email.EmailClient;
import com.adambooking.model.Email;

public class EmailClientTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void sendEmailTest() {
		//Test Email from Junit has failed ... it worked from soap ui
		Email email = new Email();
		email.setEmailFrom("haniassaad@hotmail.com");
		email.setEmailTo("haniassaad@hotmail.com");
		email.setSubject("Test Email from Adam Booking - DEV");
		email.setMessage("This Hani from Development team testing email.");
		EmailClient.SendEmail(email);

	}

}
