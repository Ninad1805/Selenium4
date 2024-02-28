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
		String emailSubject = "Automation dialogue subject Non Anonymous - 28-Feb-2024 20:35:43 #15608";
		String[] emailBody = { "SysAdmin Saurabh submitted new Dialogue via Recorder",
				"Automation dialogue subject Non Anonymous - 28-Feb-2024 20:35:43",
				"Automation dialogue description Non Anonymous - 28-Feb-2024 20:35:44", "Sankets Automation IA" };
		emailApi.fetchEmailNotification(emailId, emailPassword, emailSubject, emailBody);
	}

	@Test
	public void emailNotificationXpath() {
		String emailSubject = "Automation dialogue subject Non Anonymous - 28-Feb-2024 20:35:43 #15608";
		String[] emailBody = { "SysAdmin Saurabh submitted new Dialogue via Recorder",
				"Automation dialogue subject Non Anonymous - 28-Feb-2024 20:35:43",
				"Automation dialogue description Non Anonymous - 28-Feb-2024 20:35:44", "Sankets Automation IA" };
		emailApi.fetchEmailNotificationXpath(emailId, emailPassword, emailSubject, emailBody);
	}

	@Test
	public void createMailDialogue() {
		String emailTo = "qatesting.new@k12-lets-talk.com";
		String emailSubject = "New mail dialogue subject Ninad Sawant";
		String emailBody = "New mail dialogue description Ninad Sawant";
		emailApi.sendEmail(emailId, emailPassword, emailTo, attachment, emailSubject, emailBody);
	}

	@Test
	public void replyToEmail() {
		String receivedEmailSubject = "New mail dialogue subject";
		String replyTxt = "Reply to customer";
		String emailSubject = "Reply from customer Ninad";
		String emailBody = "Reply from customer Ninad";
		emailApi.replyToEmail(receivedEmailSubject, replyTxt, emailSubject, emailBody, attachment);
	}

}