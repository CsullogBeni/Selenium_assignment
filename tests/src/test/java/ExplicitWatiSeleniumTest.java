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
 * Testing explicit wait on https://demowebshop.tricentis.com/ website.
 */
public class ExplicitWatiSeleniumTest {

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
     * Tests searching for a product with an explicit wait.
     * This test opens the website, waits for the search box to be visible,
     * enters a search term, submits the form and waits for the search results
     * to become visible. The test then verifies that some results were found
     * by checking the length of the page text.
     */
    @Test
    public void testSearchWithExplicitWait() {
        driver.get("https://demowebshop.tricentis.com/");

        By searchBoxLocator = By.id("small-searchterms");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLocator));

        searchBox.sendKeys("laptop");
        searchBox.sendKeys(Keys.ENTER);

        By resultBlock = By.cssSelector(".product-grid");
        WebElement results = wait.until(ExpectedConditions.visibilityOfElementLocated(resultBlock));

        String pageText = results.getText();
        System.out.println("Search result text:\n" + pageText);
        Assert.assertTrue("No results found.", pageText.length() > 10);
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
