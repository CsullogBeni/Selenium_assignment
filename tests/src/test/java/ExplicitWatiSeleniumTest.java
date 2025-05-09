import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

public class ExplicitWatiSeleniumTest {

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


    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
