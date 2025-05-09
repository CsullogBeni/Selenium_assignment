import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.interactions.Actions;

import java.net.URL;
import java.net.MalformedURLException;

/**
 * Testing hovering over an element on https://demowebshop.tricentis.com/ website.
 */
public class HoverSeleniumTest {

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
     * Tests hovering over an image container on the "Build your own computer" page.
     * Verifies that the "Desktops" subcategory link becomes visible upon hovering.
     */
    @Test
    public void testHover() {
        this.driver.get("https://demowebshop.tricentis.com/build-your-own-computer");

        WebElement imageContainer = waitVisibiltyAndFindElement(By.cssSelector("div.gallery div.picture"));

        Actions actions = new Actions(driver);
        actions.moveToElement(imageContainer).perform();

        By subCategoryLocator = By.linkText("Desktops");
        WebElement subMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(subCategoryLocator));

        Assert.assertTrue("Menu is not displayed", subMenu.isDisplayed());
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
