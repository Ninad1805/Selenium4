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

	EmailApi emailApi = new EmailApi();
	String attachment = "C:\\\\\\\\Users\\\\\\\\Ninad\\\\\\\\eclipse-workspace\\\\\\\\Selenium\\\\\\\\src\\\\\\\\test\\\\\\\\java\\\\\\\\Resources\\\\\\\\1_spiderverse-iphone-wallpaper-hd (1).jpg";
	String emailId = "ninadsawantauto@outlook.com";
	String emailPassword = "Automation@1234";

	@Test
	public void emailNotification() {
		String emailSubject = "Thank You from  Live Indian acc";
		String[] emailBody = { "New mail dialogue description" };
		emailApi.fetchEmailNotification(emailId, emailPassword, emailSubject, emailBody);
	}

	@Test
	public void createMailDialogue() {
		String emailTo = "qatesting.new@k12-lets-talk.com";
		String emailSubject = "New mail dialogue subject";
		String emailBody = "New mail dialogue description";
		emailApi.sendEmail(emailId, emailPassword, emailTo, attachment, emailSubject, emailBody);
	}

	@Test
	public void replyToEmail() {
		String receivedEmailSubject = "New mail dialogue subject";
		String replyTxt = "Reply to customer";
		String emailSubject = "Reply from customer";
		String emailBody = "Reply from customer";
		emailApi.replyToEmail(receivedEmailSubject, replyTxt, emailSubject, emailBody, attachment);
	}

}