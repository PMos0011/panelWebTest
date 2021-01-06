package CommodityTest;

import DriverSetup.ChromeDriverSetup;
import POM.Commodity.Commodity;
import POM.Commodity.CommodityInputs;
import POM.Commodity.CommodityMeasures;
import POM.Commodity.TaxRate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import static POM.LoginPage.loginIntoApp;
import static POM.Sidebar.ApplicationPages.NEW_INVOICE;
import static POM.Sidebar.SidebarModule.goToPage;

public class CommodityInputsTest {

    private static WebDriver driver;
    private static Commodity commodity;

    @BeforeAll
    public static void setupAll() {
        ChromeDriverSetup chromeDriverSetup = new ChromeDriverSetup("http://localhost:3000/");
        driver = chromeDriverSetup.getWebDriver();

        loginIntoApp(driver, "a", "a");
        goToPage(driver, chromeDriverSetup.getWebDriverWait(), NEW_INVOICE);
        commodity = new Commodity(driver);
        commodity.addEmptyCommodity();

    }

    @AfterAll
    public static void clean() {
        driver.quit();
    }

    @ParameterizedTest
    @EnumSource(CommodityMeasures.class)
    public void setMeasureInNewCommodity(CommodityMeasures measure) throws InterruptedException {
        commodity.setMeasure(measure);
        String xpathString = "//*[text() = '" + measure.getMeasure() + "']";
        driver.findElement(By.xpath(xpathString));
    }


    @ParameterizedTest
    @CsvSource(value = {"1;1.00", "3,1;3.10", "5.15;5.15"}, delimiter = ';')
    public void setNetPriceInNewCommodity(String input, String expected) {
        WebElement netPrice = commodity.setInputValue(CommodityInputs.NET_PRICE, input);
        Assertions.assertEquals(expected, netPrice.getAttribute("value"));
    }

    @ParameterizedTest
    @CsvSource(value = {"1;1", "3,1;3.1", "5.15;5.15"}, delimiter = ';')
    public void setDiscountInNewCommodity(String input, String expected) {
        WebElement discount = commodity.setInputValue(CommodityInputs.DISCOUNT, input);
        Assertions.assertEquals(expected, discount.getAttribute("value"));

    }


    @ParameterizedTest
    @EnumSource(TaxRate.class)
    public void setTaxValueInNewCommodity(TaxRate taxRate) throws InterruptedException {
        commodity.setTaxRate(taxRate);
        String xpathString = "//*[text() = '" + taxRate.getTaxRate() + "']";
        driver.findElement(By.xpath(xpathString));

    }
}
