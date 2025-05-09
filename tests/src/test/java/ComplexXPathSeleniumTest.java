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
 * Testing complex xpath handling on https://demowebshop.tricentis.com/ website.
 */
public class ComplexXPathSeleniumTest {

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
     * Testing complex xpath clicking on 'Register' link 
     */
    @Test
    public void testComplexXPathClick() {
        this.driver.get("https://demowebshop.tricentis.com/");

        By registerLinkXpath = By.xpath("//div[@class='header']//a[@class='ico-register' and @href='/register']");

        WebElement registerLink = waitVisibiltyAndFindElement(registerLinkXpath);
        Assert.assertTrue(registerLink.isDisplayed());

        registerLink.click();

        WebElement heading = waitVisibiltyAndFindElement(By.cssSelector("div.page-title h1"));
        Assert.assertEquals("Register", heading.getText());
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
