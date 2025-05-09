import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

public class FromSendingSeleniumTest{
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
    public void testContactFormAfterLogin() {
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
        Assert.assertTrue(logoutLink.isDisplayed());

        WebElement contactLink = waitVisibiltyAndFindElement(By.linkText("Contact us"));
        contactLink.click();

        WebElement subjectDropdown = waitVisibiltyAndFindElement(By.id("Enquiry"));
        subjectDropdown.sendKeys("Automatic test, please do not answer.");

        WebElement submitButton = waitVisibiltyAndFindElement(By.name("send-email"));
        submitButton.click();

        WebElement resultMessage = waitVisibiltyAndFindElement(By.className("result"));
        Assert.assertTrue(resultMessage.getText().contains("Your enquiry has been successfully sent to the store owner."));
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}