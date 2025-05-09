import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

public class LoginOutSeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 10);
    }

    private final By bodyLocator = By.tagName("body");
    private final By searchTogglerLocator = By.className("search-bar-toggler");
    private final By searchLocator = By.xpath("//div[@class='sliding-search-wrapper']/form/input[@name='search']");

    private WebElement waitVisibiltyAndFindElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

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


    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
