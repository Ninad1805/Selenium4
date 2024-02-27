package Selenium.Selenium;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
    WebDriver driver;
    //EmailApi emailApi = new EmailApi();

    @BeforeTest
    public void startWebDriver() {

        //CODE FOR MOBILE EMULATION
        Map<String,String> mobileEm = new HashMap<String,String>();
        mobileEm.put("deivceName","iPhoneX");

        ChromeOptions opt = new ChromeOptions();
        //opt.addArguments("--headless");
        //opt.addArguments("incognito");
        opt.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        opt.setExperimentalOption("mobileEmulation",mobileEm);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(opt);
        //driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

    }

    @AfterTest
    public void closeWebDriver() {
        driver.quit();
    }
}
