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

public class EmailApi extends BaseClass {

	public void fetchEmailNotification(String emailId, String emailPassword, String EmailSubject, String[] emailBody) {
		// Email configuration details
		String host = "outlook.office365.com";
		String username = emailId;
		String password = emailPassword;
		// Set the properties for the JavaMail session
		Properties properties = new Properties();
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.imaps.host", host);
		properties.put("mail.imaps.port", "993");
		properties.put("mail.imaps.starttls.enable", "true");
		// Set the timeout (in milliseconds)
		long timeout = 500 * 1000; // 100 seconds
		try {
			int j = 0;
			;
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
				int startMessageIndex = Math.max(0, totalMessageCount - 10);
				Message[] messages = inbox.getMessages(startMessageIndex, totalMessageCount);
				// Check each message for the desired subject and body
				for (int k = messages.length - 1; k > 0; k--) {
					Object content = messages[k].getContent();
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
								if (messages[k].getSubject().contains(EmailSubject)
										&& textContent.contains(emailBody[0])) {
									for (j = 0; j < emailBody.length; j++) {
										Assert.assertTrue(textContent.contains(emailBody[j]));
										System.out.println("The text \"" + emailBody[j] + "\" exists in the mail body");
									}
									if (emailBody.length == j) {
										Assert.assertTrue(true, "The mail is successfully fetched");
										return;
									}
								}
							}
						}
					}
				}
				inbox.close(false);
				store.close();
				TimeUnit.SECONDS.sleep(10); // Check every 10 seconds
			}
			Assert.fail("Desired email not found within the timeout period.");

		} catch (

		Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	public void sendEmail(String emailId, String emailPassword, String emailTo, String attachment, String emailSubject,
			String emailBody) {

		final String from = emailId;
		final String password = emailPassword;
		String to = emailTo;
		String subject = emailSubject;
		String body = emailBody;
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

	public void replyToEmail(String receivedEmailSubject, String repltTxt, String emailSubject, String emailBody,
			String attachment) {
		// Email configuration details
		String host = "outlook.office365.com";
		String username = "ninadsawantauto@outlook.com";
		String password = "Automation@1234";
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
					if (message.getSubject().contains(receivedEmailSubject)) {
						Object content = message.getContent();
						if (content instanceof String) {
						} else if (content instanceof Multipart) {
							// If content is multipart (e.g., HTML)
							Multipart multipart = (Multipart) content;
							for (int i = 0; i < multipart.getCount(); i++) {
								BodyPart bodyPart = multipart.getBodyPart(i);
								if (bodyPart.getContentType().contains("text/html")) {
									String htmlContent = (String) bodyPart.getContent();
									Document document = Jsoup.parse(htmlContent);
									String textContent = document.text();
									if (textContent.contains(repltTxt)) {
										sendEmail(username, password, InternetAddress.toString(message.getReplyTo()),
												attachment, emailSubject, emailBody);
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
			Assert.fail("Desired email not found within the timeout period.");
			// Close the resources

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fetchEmailNotificationXpath(String emailId, String emailPassword, String EmailSubject,
			String[] emailBody) {
		// Email configuration details
		String host = "outlook.office365.com";
		String username = emailId;
		String password = emailPassword;
		// Set the properties for the JavaMail session
		Properties properties = new Properties();
		properties.put("mail.store.protocol", "imaps");
		properties.put("mail.imaps.host", host);
		properties.put("mail.imaps.port", "993");
		properties.put("mail.imaps.starttls.enable", "true");
		// Set the timeout (in milliseconds)
		long timeout = 500 * 1000; // 100 seconds
		try {
			int j = 0;
			;
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
				int startMessageIndex = Math.max(0, totalMessageCount - 10);
				Message[] messages = inbox.getMessages(startMessageIndex, totalMessageCount);
				// Check each message for the desired subject and body
				for (int k = messages.length - 1; k > 0; k--) {
					Object content = messages[k].getContent();
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

								if (messages[k].getSubject().contains(EmailSubject)) {
									for (j = 0; j < emailBody.length; j++) {
										String html = emailBody[j] + "</div>";
										if (htmlContent.contains(html)) {
											System.out.println("Email contains " + emailBody[j]);
										}
									}
									if (emailBody.length == j) {
										Assert.assertTrue(true, "The mail is successfully fetched");
										return;

									}

								}
							}
						}
					}
				}
			}
			// inbox.close(false);
			store.close();
			TimeUnit.SECONDS.sleep(10); // Check every 10 seconds
			// }
			Assert.fail("Desired email not found within the timeout period.");

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}
}