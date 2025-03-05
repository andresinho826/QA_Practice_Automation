package Saudemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Saudemo_Lab {

    WebDriver driver;
    String urlPage = "https://www.saucedemo.com/";
    String chromeDriverPath = "src/test/resources/chromedriver.exe";


    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(urlPage);
    }


    @Test
    public void loginTest() throws InterruptedException {
        /*
        #login-button
        #password
        #user-name

        standard_user
        secret_sauce
         */

        WebElement usuario = driver.findElement(By.cssSelector("#user-name"));
        WebElement pass = driver.findElement(By.cssSelector("#password"));
        WebElement btnLogin = driver.findElement(By.cssSelector("#login-button"));

        usuario.sendKeys("standard_user");

        //Thread.sleep(100);
        pass.sendKeys("secret_sauce");
        //Thread.sleep(100);
        btnLogin.click();

    }

    @AfterMethod
    public void tearDown(){
        driver.quit();  // cierra todas las ventanas
        //driver.close(); // solo cierrra la ventana actual
    }
}
