package Saudemo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Saudemo_test {

    WebDriver driver;
    WebDriverWait wait;
    String urlPage = "https://www.saucedemo.com/";
    String chromeDriverPath = "src/test/resources/chromedriver.exe";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--incognito");

        // Desactiva el aviso de guardar contraseña de Chrome
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");



        // Desactiva los servicios de guardado de contraseñas
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));



        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Esperas explícitas
        driver.manage().window().maximize();
        driver.get(urlPage);
    }

    @Test
    public void loginTest() {
        WebElement usuario = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement pass = driver.findElement(By.id("password"));
        WebElement btnLogin = driver.findElement(By.id("login-button"));

        usuario.sendKeys("standard_user");
        pass.sendKeys("secret_sauce");
        btnLogin.click();


        // Validación de inicio de sesión
        WebElement productPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Products')]")));
        Assert.assertTrue(productPage.isDisplayed(), "No se encontró la página de productos, el login falló.");
    }

    @Test
    public void buyAProduct() {
        loginTest(); // Reutilizar el login antes de comprar un producto

        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack")));
        addToCartBtn.click();

        WebElement shoppingCartBtn = driver.findElement(By.cssSelector(".shopping_cart_link"));
        shoppingCartBtn.click();

        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        checkoutBtn.click();

        WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        WebElement lastName = driver.findElement(By.id("last-name"));
        WebElement zipCode = driver.findElement(By.id("postal-code"));
        WebElement continueBtn = driver.findElement(By.id("continue"));

        firstName.sendKeys("cualquier");
        lastName.sendKeys("cosa");
        zipCode.sendKeys("100221525");
        continueBtn.click();

        WebElement finishBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("finish")));
        finishBtn.click();

        WebElement successOrder = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".complete-header")));
        Assert.assertTrue(successOrder.isDisplayed(), "No se encontró el mensaje de compra exitosa.");

        WebElement backHomeBtn = driver.findElement(By.id("back-to-products"));
        backHomeBtn.click();

        WebElement homePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".app_logo")));
        Assert.assertTrue(homePage.isDisplayed(), "No se regresó a la página principal.");
    }

    @Test
    public void validateErrorMessage() {
        loginTest(); // Reutilizar el login antes de comprar un producto


        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-bike-light")));
        addToCartBtn.click();

        WebElement shoppingCartBtn = driver.findElement(By.cssSelector(".shopping_cart_link"));
        shoppingCartBtn.click();

        WebElement removeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("remove-sauce-labs-bike-light")));
        removeBtn.click();


        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        checkoutBtn.click();

        WebElement continueBtn = driver.findElement(By.id("continue"));
        continueBtn.click();

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Error: First Name is required')]")));
        Assert.assertTrue(errorMessage.isDisplayed(), "No se encontró el mensaje de error.");


    }

    @Test
    public void randomProduct() {
        loginTest(); // Reutilizar el login antes de comprar un producto

        //WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack")));
        //addToCartBtn.click();

        List<WebElement> listProducts = driver.findElements(By.cssSelector(".inventory_item_name"));

        Random random = new Random();
        int productRandom = random.nextInt(listProducts.size());
        WebElement selectedProduct = listProducts.get(productRandom);
        selectedProduct.click();
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Add')]")));
        addToCartBtn.click();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void lockOutUserTest() {
        WebElement usuario = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement pass = driver.findElement(By.id("password"));
        WebElement btnLogin = driver.findElement(By.id("login-button"));

        usuario.sendKeys("locked_out_user");
        pass.sendKeys("secret_sauce");
        btnLogin.click();


        // Validación mensaje de error "Epic sadface: Sorry, this user has been locked out."
        WebElement productPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Epic')]")));
        Assert.assertTrue(productPage.isDisplayed(), "No se encontró la página de productos, el login falló.");
    }

    @Test
    public void userAndPassInvalidTest() {
        WebElement usuario = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement pass = driver.findElement(By.id("password"));
        WebElement btnLogin = driver.findElement(By.id("login-button"));

        usuario.sendKeys("andres");
        pass.sendKeys("secret_sauce");
        btnLogin.click();


        // Validación mensaje de error "Epic sadface: Username and password do not match any user in this service"
        WebElement productPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Username and password')]")));
        Assert.assertTrue(productPage.isDisplayed(), "No se encontró la página de productos, el login falló.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

