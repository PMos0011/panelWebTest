package CommodityTest;

import DriverSetup.ChromeDriverSetup;
import POM.Invoice.Commodity;
import POM.Invoice.InvoiceInputs;
import POM.Invoice.CommodityMeasures;
import POM.Invoice.TaxRate;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static POM.LoginPage.loginIntoApp;
import static POM.Sidebar.ApplicationPages.NEW_INVOICE;
import static POM.Sidebar.SidebarModule.goToPage;

@RunWith(JUnitParamsRunner.class)
public class SingleCommodityCalculationTest {

    private static WebDriver driver;
    private static Commodity commodity;

    @BeforeClass
    public static void setupAll() throws InterruptedException {
        ChromeDriverSetup chromeDriverSetup = new ChromeDriverSetup("http://localhost:3000/");
        driver = chromeDriverSetup.getWebDriver();

        loginIntoApp(driver, "a", "a");
        goToPage(driver, chromeDriverSetup.getWebDriverWait(), NEW_INVOICE);
        commodity = new Commodity(driver);

        commodity.addEmptyCommodity();
        commodity.setTaxRate(TaxRate._23);
        commodity.setMeasure(CommodityMeasures.ART);

    }

    @AfterClass
    public static void clean() {
        driver.quit();
    }

    private Object[] netPriceParameters() {
        return new Object[]{
                new Object[]{"1", "10", "0", "12.30", "10.00", "2.30", "12.30"},
                new Object[]{"1", "20", "2", "24.11", "19.60", "4.51", "24.11"},
                new Object[]{"5", "15.7", "1.25", "19.07", "77.50", "17.83", "95.33"}
        };
    }

    @Test
    @Parameters(method = "netPriceParameters")
    public void netPriceChange(String amount, String netPrice, String discount,
                               String grossPrice, String netAmount, String taxAmount, String grossAmount) {

        commodity.setInputValue(InvoiceInputs.AMOUNT, amount, 0);
        commodity.setInputValue(InvoiceInputs.NET_PRICE, netPrice, 0);
        commodity.setInputValue(InvoiceInputs.DISCOUNT, discount, 0);

        checkAssertions(grossPrice, netAmount, taxAmount, grossAmount);

    }

    private Object[] grossPriceParameters() {
        return new Object[]{
                new Object[]{"1", "0", "100", "100.00", "81.30", "18.70", "100.00"},
                new Object[]{"1", "0", "520", "519.99", "422.76", "97.23", "519.99"},
                new Object[]{"5", "0,75", "300.00", "300.00", "1219.50", "280.49", "1499.99"},
                new Object[]{"7", "0,25", "299,99", "299.98", "1707.23", "392.66", "2099.89"}
        };
    }

    @Test
    @Parameters(method = "grossPriceParameters")
    public void grossPriceChange(String amount, String discount, String grsPrice,
                                 String grossPrice, String netAmount, String taxAmount, String grossAmount) {

        commodity.setInputValue(InvoiceInputs.AMOUNT, amount, 0);
        commodity.setInputValue(InvoiceInputs.DISCOUNT, discount, 0);
        commodity.setInputValue(InvoiceInputs.GROSS_PRICE, grsPrice, 0);

        checkAssertions(grossPrice, netAmount, taxAmount, grossAmount);

    }

    private void checkAssertions(String grossPrice, String netAmount, String taxAmount, String grossAmount) {
        checkSingleAssertion(InvoiceInputs.GROSS_PRICE, grossPrice);
        checkSingleAssertion(InvoiceInputs.NET_AMOUNT, netAmount);
        checkSingleAssertion(InvoiceInputs.TAX_AMOUNT, taxAmount);
        checkSingleAssertion(InvoiceInputs.GROSS_AMOUNT, grossAmount);
    }

    private void checkSingleAssertion(InvoiceInputs commodityInput, String expectedValue) {
        Assert.assertEquals(expectedValue,
                driver.findElement(By.name(commodityInput.getInput()))
                        .getAttribute("value"));
    }
}
