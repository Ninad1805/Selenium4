package Selenium.Selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

public class Tables extends BaseClass {
	@Test
	public void printTable() {
		driver.get("https://money.rediff.com/gainers");
		WebElement table = driver.findElement(By.xpath("//table[@class='dataTable']"));
		List<WebElement> rowsNum = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr"));
		List<WebElement> colNum = driver.findElements(By.xpath("//table[@class='dataTable']/tbody/tr[1]/td"));
		System.out.println("Toal number of rows: " + rowsNum.size());
		System.out.println("Total number of columns: " + colNum.size());

		for (int rows = 1; rows <= rowsNum.size(); rows++) {
			for (int cols = 1; cols <= colNum.size(); cols++) {
				// System.out.println(driver.findElement(By.xpath("//table[@class='dataTable']/tbody/tr["
				// + rows + "]/td[" + cols + "]")));
				System.out.print(driver
						.findElement(By.xpath("//table[@class='dataTable']/tbody/tr[" + rows + "]/td[" + cols + "]"))
						.getText() + "   ");

			}
			System.out.println();

		}
	}

	@Test
	public void printAllLinks() {
		driver.get("https://www.way2automation.com/");
		List<WebElement> links = driver.findElements(By.xpath("//a"));
		int numOfLinks = links.size();
		System.out.println("The total number of links are: " + numOfLinks);

		for (int i = 1; i < numOfLinks; i++) {
			if (!driver.findElement(By.xpath("(//a)[" + i + "]")).getText().isEmpty()) {
				System.out.println(driver.findElement(By.xpath("(//a)[" + i + "]")).getText());
			}
		}
	}
}
