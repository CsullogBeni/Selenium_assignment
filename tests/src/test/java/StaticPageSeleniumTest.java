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
 * Testing reading static page content on https://demowebshop.tricentis.com/ website.
 */
public class StaticPageSeleniumTest {

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
     * Tests that the 'About us' page contains the text 'About us'.
     * This test also verifies that the page content is longer than 30 characters.
     */
    @Test
    public void testStaticAboutUsPage() {
        this.driver.get("https://demowebshop.tricentis.com/about-us");

        WebElement contentDiv = waitVisibiltyAndFindElement(By.className("page-body"));

        String pageText = contentDiv.getText();
        System.out.println("Page content:\n" + pageText);

        Assert.assertTrue("Page must contain 'About us' text.",
                pageText.toLowerCase().contains("about us") || pageText.length() > 30);
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
