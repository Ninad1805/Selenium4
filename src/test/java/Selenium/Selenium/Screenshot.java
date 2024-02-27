package Selenium.Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Screenshot extends BaseClass {

    @Test
    public void fullPageScreenshot() throws IOException {
        WebDriverManager.firefoxdriver().setup();
        FirefoxDriver driver1 = new FirefoxDriver();
        driver1.manage().window().maximize();
        driver1.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver1.get("https://www.way2automation.com/way2auto_jquery/index.php");
        WebElement table = driver1.findElement(By.xpath("(//input[@class='button'])[2]"));
        File screenShot = table.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShot,new File("./src/java/Resources/screenshot/above.jpg"));

        File pageScreenshot =  driver1.getFullPageScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(pageScreenshot,new File("./src/java/Resources/screenshot/above.jpg"));

        driver1.quit();
    }

    @Test
    public void SsChrome() throws IOException {
        //NO FULL PAGE SCREENSHOT FOR CHROME
        driver.get("https://www.way2automation.com/way2auto_jquery/index.php");
        File pageScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(pageScreenshot, new File("./src/test/java/Resources/screenshot/above.jpg"));
    }
}
