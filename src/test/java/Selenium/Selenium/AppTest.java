package Selenium.Selenium;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.checkerframework.common.util.report.qual.ReportCall;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest extends BaseClass {

	@Test
	public void shouldAnswerWithTrue() {
		driver.get("https://mail.rediff.com/cgi-bin/login.cgi");
		driver.findElement(By.name("proceed")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		System.out.println(alert.getText());
		alert.accept();

		driver.quit();
	}

	@Test
	public void checkBox() throws InterruptedException {
		driver.get("http://www.tizag.com/htmlT/htmlcheckboxes.php");
		try {
			driver.findElement(By.xpath("(//input[@name=\"sports\" and @type=\"checkbox\" and @value=\"soccer\"])[1]"))
					.isSelected();
			Thread.sleep(2000);
		} catch (Exception e) {
			driver.findElement(By.xpath("(//input[@name=\"sports\" and @type=\"checkbox\" and @value=\"soccer\"])[1]"))
					.click();
			Thread.sleep(4000);
		}

	}
}
