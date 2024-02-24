package Selenium.Selenium;

import javax.mail.*;
import javax.mail.internet.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;
import java.io.*;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class EmailApiTest extends BaseClass {

	emailApi emailapi = new emailApi();

	@Test
	public void emailNotification() {
		String emailSubject = "Manchester United";
		String[] emailBody = { "unravel.", "Spain", "Erin Collantes, a British teacher in Spain, finds herself caugh",
				"Erin Collantes, a British teacher in Spain, finds herself caugh" };
		System.out.println(emailBody);
		emailapi.fetchEmailNotification(emailSubject, emailBody);
	}

	@Test
	public void createMailDialogue() {
		String emailFrom = "ninad1805@outlook.com";
		String emailTo = "qatesting.new@k12-lets-talk.com";
		emailapi.sendEmail(emailFrom, emailTo,
				"C:\\\\Users\\\\Ninad\\\\eclipse-workspace\\\\Selenium\\\\src\\\\test\\\\java\\\\Resources\\\\1_spiderverse-iphone-wallpaper-hd (1).jpg");
	}

	@Test
	public void repltToEmail() {
		String emailSubject = "Ninad's Email Internal Dialogue";
		String replyTxt = "Reply to customer";
		emailapi.replyToEmail(emailSubject, replyTxt);
	}

}