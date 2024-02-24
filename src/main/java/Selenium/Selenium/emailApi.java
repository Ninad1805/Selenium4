package Selenium.Selenium;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;

public class emailApi extends BaseClass {

	public void fetchEmailNotification(String EmailSubject, String[] emailBody) {
		// Email configuration details
		String host = "outlook.office365.com";
		String username = "ninad1805@outlook.com";
		String password = "United@1805*";
		// Set the properties for the JavaMail session
		Properties properties = new Properties();
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.imaps.host", host);
		properties.put("mail.imaps.port", "993");
		properties.put("mail.imaps.starttls.enable", "true");
		// Set the timeout (in milliseconds)
		long timeout = 200 * 1000; // 100 seconds
		try {
			// Create a Session object and connect to the email server
			Session session = Session.getDefaultInstance(properties);
			Store store = session.getStore("imaps");

			// Start time for timeout
			long startTime = System.currentTimeMillis();
			// Loop until the timeout is reached
			while (System.currentTimeMillis() - startTime < timeout) {
				// Fetch recent messages
				store.connect(host, username, password);
				// Open the inbox folder
				Folder inbox = store.getFolder("INBOX");
				inbox.open(Folder.READ_ONLY);
				int totalMessageCount = inbox.getMessageCount();
				int startMessageIndex = Math.max(0, totalMessageCount - 5);
				Message[] messages = inbox.getMessages(startMessageIndex, totalMessageCount);
				// Check each message for the desired subject and body
				for (Message message : messages) {
					if (message.getSubject().contains(EmailSubject)) {
						// System.out.println(message.getSubject());
						System.out.println("From: " + InternetAddress.toString(message.getFrom()));
						System.out.println("Subject: " + message.getSubject());
						System.out.println("Date: " + message.getSentDate());
						System.out.println("Content: ");
						Object content = message.getContent();
						if (content instanceof String) {
							// If content is plain text
							// System.out.println(content);
						} else if (content instanceof Multipart) {
							// If content is multipart (e.g., HTML)
							Multipart multipart = (Multipart) content;
							for (int i = 0; i < multipart.getCount(); i++) {
								BodyPart bodyPart = multipart.getBodyPart(i);
								if (bodyPart.getContentType().contains("text/html")) {
									String htmlContent = (String) bodyPart.getContent();
									Document document = Jsoup.parse(htmlContent);
									String textContent = document.text();
									for (int j = 0; j < emailBody.length; j++) {
										if (textContent.contains(emailBody[j])) {
											Assert.assertTrue(textContent.contains(emailBody[j]));
											System.out.println("The text " + emailBody[j] + " exists in the mail body");
										}else {
											System.out.println("The text "+emailBody[j]+" does not exists in the mail body");
											Assert.fail();
										}
									}
									System.out.println(textContent);
									// Exit the loop if the desired email is found
									return;
								}
							}
						}
					}
				}
				inbox.close(false);
				store.close();

				// Sleep for a short interval before checking again
				TimeUnit.SECONDS.sleep(10); // Check every 10 seconds
			}
			// If the desired email is not found within the timeout period
			System.out.println("Desired email not found within the timeout period.");
			// Close the resources

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendEmail(String emailFrom, String emailTo, String attachment) {

		final String from = emailFrom;
		final String password = "United@1805*";
		String to = emailTo;
		String subject = "Test Email from Java";
		String body = "This is a test email sent from Java.";
		// SMTP server configuration (Gmail SMTP server used in this example)
		String host = "smtp-mail.outlook.com";
		int port = 587;
		// Set additional properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		// Create a session with authentication
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		try { // Create a message
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			// Create a multipart message
			Multipart multipart = new MimeMultipart();
			// Create and add text part
			BodyPart textPart = new MimeBodyPart();
			textPart.setText(body);
			multipart.addBodyPart(textPart);
			// Attach file
			String filePath = attachment;
			BodyPart filePart = new MimeBodyPart();
			((MimeBodyPart) filePart).attachFile(new File(filePath));
			multipart.addBodyPart(filePart);
			// Set the multipart as the message's content
			message.setContent(multipart);
			// Send the message
			Transport.send(message);
			System.out.println("Email with attachment sent successfully!");
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}

	public void replyToEmail(String EmailSubject, String repltTxt) {
		// Email configuration details
		String host = "outlook.office365.com";
		String username = "ninad1805@outlook.com";
		String password = "United@1805*";
		// Set the properties for the JavaMail session
		Properties properties = new Properties();
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.imaps.host", host);
		properties.put("mail.imaps.port", "993");
		properties.put("mail.imaps.starttls.enable", "true");
		// Set the timeout (in milliseconds)
		long timeout = 200 * 1000; // 100 seconds
		try {
			// Create a Session object and connect to the email server
			Session session = Session.getDefaultInstance(properties);
			Store store = session.getStore("imaps");

			// Start time for timeout
			long startTime = System.currentTimeMillis();
			// Loop until the timeout is reached
			while (System.currentTimeMillis() - startTime < timeout) {
				// Fetch recent messages
				store.connect(host, username, password);
				// Open the inbox folder
				Folder inbox = store.getFolder("INBOX");
				inbox.open(Folder.READ_ONLY);
				int totalMessageCount = inbox.getMessageCount();
				int startMessageIndex = Math.max(0, totalMessageCount - 5);
				Message[] messages = inbox.getMessages(startMessageIndex, totalMessageCount);
				// Check each message for the desired subject and body
				for (Message message : messages) {
					if (message.getSubject().contains(EmailSubject)) {
						// System.out.println(message.getSubject());
						System.out.println("From: " + InternetAddress.toString(message.getFrom()));
						System.out.println("Subject: " + message.getSubject());
						System.out.println("Date: " + message.getSentDate());
						System.out.println("Content: ");
						Object content = message.getContent();
						if (content instanceof String) {
							// If content is plain text
							// System.out.println(message);
						} else if (content instanceof Multipart) {
							// If content is multipart (e.g., HTML)
							Multipart multipart = (Multipart) content;
							for (int i = 0; i < multipart.getCount(); i++) {
								BodyPart bodyPart = multipart.getBodyPart(i);
								if (bodyPart.getContentType().contains("text/html")) {
									String htmlContent = (String) bodyPart.getContent();
									Document document = Jsoup.parse(htmlContent);
									String textContent = document.text();
									// System.out.println(Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()));
									if (textContent.contains(repltTxt)) {
										System.out.println(textContent);
										System.out.println(InternetAddress.toString(message.getReplyTo()));
										sendEmail(username, InternetAddress.toString(message.getReplyTo()),
												"C:\\\\Users\\\\Ninad\\\\eclipse-workspace\\\\Selenium\\\\src\\\\test\\\\java\\\\Resources\\\\1_spiderverse-iphone-wallpaper-hd (1).jpg");
										return;
									}
								}
							}
						}
					}
				}
				inbox.close(false);
				store.close();

				// Sleep for a short interval before checking again
				TimeUnit.SECONDS.sleep(10); // Check every 10 seconds
			}
			// If the desired email is not found within the timeout period
			System.out.println("Desired email not found within the timeout period.");
			// Close the resources

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}