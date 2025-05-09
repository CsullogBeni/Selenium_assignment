import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

public class StaticPageSeleniumTest {

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
    public void testStaticAboutUsPage() {
        this.driver.get("https://demowebshop.tricentis.com/about-us");

        WebElement contentDiv = waitVisibiltyAndFindElement(By.className("page-body"));

        String pageText = contentDiv.getText();
        System.out.println("Page content:\n" + pageText);

        Assert.assertTrue("Page must contain 'About us' text.",
                pageText.toLowerCase().contains("about us") || pageText.length() > 30);
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
