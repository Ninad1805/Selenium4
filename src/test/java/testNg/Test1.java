package testNg;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Test1 {
	
	//CODE FOR SOFT ASSERT
	SoftAssert softAssert = new SoftAssert();

	@Test
	public void doLogin() {
		String expected = "Ninad";
		String actual = "Ninad1";
		softAssert.assertEquals(expected, actual, "Not validated");

		System.out.println("Ending");
		softAssert.assertAll();
	}
	
	
}
