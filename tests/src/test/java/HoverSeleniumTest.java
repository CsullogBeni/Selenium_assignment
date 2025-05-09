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

public class HoverSeleniumTest {

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
    public void testHover() {
        this.driver.get("https://demowebshop.tricentis.com/build-your-own-computer");

        WebElement imageContainer = waitVisibiltyAndFindElement(By.cssSelector("div.gallery div.picture"));

        Actions actions = new Actions(driver);
        actions.moveToElement(imageContainer).perform();

        By subCategoryLocator = By.linkText("Desktops");
        WebElement subMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(subCategoryLocator));

        Assert.assertTrue("Menu is not displayed", subMenu.isDisplayed());
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
