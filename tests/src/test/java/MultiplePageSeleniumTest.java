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
 * Testing visiting multiple static pages on https://demowebshop.tricentis.com/ website.
 */
public class MultiplePageSeleniumTest {

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
     * Tests visiting multiple static pages from an array and verifying
     * that the content of each page is visible.
     */
    @Test
    public void testMultipleStaticPagesFromArray() {
        String[] urls = {
            "https://demowebshop.tricentis.com/about-us",
            "https://demowebshop.tricentis.com/privacy-policy",
            "https://demowebshop.tricentis.com/conditions-of-use",
            "https://demowebshop.tricentis.com/shipping-returns"
        };

        for (String url : urls) {
            System.out.println("Test init for this page:  " + url);
            this.driver.get(url);

            WebElement contentDiv = waitVisibiltyAndFindElement(By.className("page-body"));

            String text = contentDiv.getText();
            System.out.println("Content length: " + text.length());
        }
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
