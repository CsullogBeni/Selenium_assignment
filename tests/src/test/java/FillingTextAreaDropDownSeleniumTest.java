import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.List;
import java.net.MalformedURLException;

/**
 * Testing filling text area and drop down on https://demowebshop.tricentis.com/ website.
 */
public class FillingTextAreaDropDownSeleniumTest {

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
     * Testing filling and submitting a newsletter subscription form.
     * Verifies that the newsletter subscription is successful and an
     * appropriate message is displayed.
     */
    @Test
    public void testFillingTextArea() {
        driver.get("https://demowebshop.tricentis.com/");

        WebElement newsletterInput = waitVisibiltyAndFindElement(By.id("newsletter-email"));
        newsletterInput.clear();
        newsletterInput.sendKeys("testuser@example.com");

        WebElement subscribeButton = driver.findElement(By.id("newsletter-subscribe-button"));
        subscribeButton.click();

        By resultLocator = By.id("newsletter-result-block");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(resultLocator, "Thank"));
        WebElement newsletterResult = driver.findElement(resultLocator);
        String resultText = newsletterResult.getText();
        System.out.println("Newsletter response text: '" + resultText + "'");
        Assert.assertTrue("Subscribe went wrong.", resultText.contains("Thank you") || resultText.contains("already subscribed"));
    }

    /**
     * Testing selecting an option from a drop down.
     */
    @Test
    public void testSelectFromDropDown() {
        driver.get("https://demowebshop.tricentis.com/desktops");

        By dropdownLocator = By.id("products-orderby");
        WebElement sortByDropdown = waitVisibiltyAndFindElement(dropdownLocator);

        Select select = new Select(sortByDropdown);
        select.selectByVisibleText("Price: Low to High");

        sortByDropdown = waitVisibiltyAndFindElement(dropdownLocator);
        select = new Select(sortByDropdown);

        String selectedOption = select.getFirstSelectedOption().getText();
        Assert.assertEquals("Price: Low to High", selectedOption);
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
