package CommodityTest;

import DriverSetup.ChromeDriverSetup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static POM.LoginPage.loginIntoApp;
import static POM.Sidebar.ApplicationPages.MY_DATA;
import static POM.Sidebar.ApplicationPages.NEW_INVOICE;
import static POM.Sidebar.SidebarModule.goToPage;

public class CommodityTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setupAll() {
        ChromeDriverSetup chromeDriverSetup = new ChromeDriverSetup("http://localhost:3000/");
        driver = chromeDriverSetup.getWebDriver();
        wait = chromeDriverSetup.getWebDriverWait();

        loginIntoApp(driver, "a", "a");
    }

    @BeforeEach
    public void setup() {
        goToPage(driver, wait, MY_DATA);
    }

    @AfterAll
    public static void clean() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvSource({"1,1", " 3,3", "5,5"})
    public void addCommodityFromSelector(int input, int expected) throws InterruptedException {
        goToPage(driver, wait, NEW_INVOICE);

        for (int i = 0; i < input; i++) {
            wait.until(e -> driver.findElement(By.id("commoditySelect"))).click();
            Thread.sleep(100);
            driver.findElement(By.xpath("//*[@id=\"commoditySelect\"]/div/div[1]/div[1]")).click();
            driver.findElement(By.id("addFromSelectOption")).click();
        }
        List<WebElement> commodityList = driver.findElements(By.id("counterRow"));
        Assertions.assertEquals(expected, commodityList.size());
    }

    @Test
    public void addEmptyCommodity() {
        goToPage(driver, wait, NEW_INVOICE);
        driver.findElement(By.id("addEmptyCommodity")).click();

        List<WebElement> commodityList = driver.findElements(By.id("counterRow"));
        Assertions.assertEquals(1, commodityList.size());
    }
}
