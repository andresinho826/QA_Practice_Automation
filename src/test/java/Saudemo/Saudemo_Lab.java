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
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(urlPage);
    }


    @Test
    public void loginTest() throws InterruptedException {

        WebElement usuario = driver.findElement(By.cssSelector("#user-name"));
        WebElement pass = driver.findElement(By.cssSelector("#password"));
        WebElement btnLogin = driver.findElement(By.cssSelector("#login-button"));
        usuario.sendKeys("standard_user");
        pass.sendKeys("secret_sauce");
        btnLogin.click();

    }

    @Test
    public void buyAProduct(){
        WebElement productPage = driver.findElement(By.xpath("//*[contains(text(), 'Products')]"));
        WebElement addToCartBtn = driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-backpack"));
        WebElement shoppingCartBtn = driver.findElement(By.cssSelector(".shopping_cart_link"));
        WebElement yourCartPage = driver.findElement(By.xpath("//span[@class='title' and text()='Your Cart']"));
        WebElement checkoutBtn = driver.findElement(By.cssSelector("#checkout"));
        WebElement checkOutInfoPage = driver.findElement(By.xpath("//span[@class='title' and text()='Checkout: Your Information']"));
        WebElement firstName = driver.findElement(By.cssSelector("#first-name"));
        WebElement lastName = driver.findElement(By.cssSelector("#last-name"));
        WebElement zipCode = driver.findElement(By.cssSelector("#postal-code"));
        WebElement continueBtn = driver.findElement(By.cssSelector("#continue"));
        WebElement overviewPage = driver.findElement(By.xpath("//span[@class='title' and text()='Checkout: Overview']"));
        WebElement finishBtn = driver.findElement(By.cssSelector("#finish"));
        WebElement completePage = driver.findElement(By.xpath("//span[@class='title' and text()='Checkout: Complete!']"));
        WebElement successOrder = driver.findElement(By.cssSelector(".complete-header"));
        WebElement backHomeBtn = driver.findElement(By.cssSelector("#back-to-products"));
        WebElement homePage = driver.findElement(By.cssSelector(".app_logo"));


        productPage.isDisplayed();
        addToCartBtn.click();
        shoppingCartBtn.click();

        yourCartPage.isDisplayed();
        checkoutBtn.click();

        checkOutInfoPage.isDisplayed();
        firstName.sendKeys("cualquier");
        lastName.sendKeys("cosa");
        zipCode.sendKeys("100221525");
        continueBtn.click();

        overviewPage.isDisplayed();
        finishBtn.click();

        completePage.isDisplayed();
        successOrder.isDisplayed();
        backHomeBtn.click();

        homePage.isDisplayed();
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();  // cierra todas las ventanas
        //driver.close(); // solo cierrra la ventana actual
    }
}
