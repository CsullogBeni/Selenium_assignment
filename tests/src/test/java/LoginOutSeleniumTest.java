import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * Testing login and log out on https://demowebshop.tricentis.com/ website.
 */
public class LoginOutSeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    /**
     * Sets up the test environment by creating a new driver instance
     * and maximising the window. The driver is then stored in the
     * {@link #driver} field and a {@link WebDriverWait} is created
     * to wait up to 10 seconds for elements to be visible.
     * 
     * @throws MalformedURLException if the URL passed to the
     *         {@link RemoteWebDriver} constructor is not a valid URL.
     */
    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 10);
    }

    private WebElement waitVisibiltyAndFindElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    /**
     * Tests login and logout functionality.
     * Verifies that the login is successful and that the logout link is displayed.
     * Then verifies that the logout is successful and that the login link is displayed again.
     */
    @Test
    public void testLoginAndLogout() {
        this.driver.get("https://demowebshop.tricentis.com/");

        WebElement loginLink = waitVisibiltyAndFindElement(By.className("ico-login"));
        loginLink.click();

        WebElement emailInput = waitVisibiltyAndFindElement(By.id("Email"));
        emailInput.sendKeys("asddsa@gmail.com");

        WebElement passwordInput = waitVisibiltyAndFindElement(By.id("Password"));
        passwordInput.sendKeys("asdasd");

        WebElement loginButton = waitVisibiltyAndFindElement(By.cssSelector("input.button-1.login-button"));
        loginButton.click();

        WebElement logoutLink = waitVisibiltyAndFindElement(By.className("ico-logout"));
        Assert.assertTrue("Successful login.", logoutLink.isDisplayed());

        logoutLink.click();

        WebElement loginLinkAgain = waitVisibiltyAndFindElement(By.className("ico-login"));
        Assert.assertTrue("Successful logout.", loginLinkAgain.isDisplayed());
    }

    /**
     * Closes the browser after each test.
     */
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
