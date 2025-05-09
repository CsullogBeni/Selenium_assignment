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
 * Testing reading page title on https://demowebshop.tricentis.com/ website.
 */
public class ReadPageTitleSeleniumTest {

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

    /**
     * Tests reading the page title of the demo web shop website.
     * This test opens the website and retrieves the page title,
     * then asserts that the title contains 'Demo Web Shop' text.
     */

    @Test
    public void testReadPageTitle() {
        driver.get("https://demowebshop.tricentis.com/");

        String actualTitle = driver.getTitle();
        System.out.println("Title of the page: " + actualTitle);

        Assert.assertTrue("The title must contain 'Demo Web Shop' text.",
                actualTitle.contains("Demo Web Shop"));
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
