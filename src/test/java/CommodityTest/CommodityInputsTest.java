package CommodityTest;

import DriverSetup.ChromeDriverSetup;
import POM.Invoice.Commodity;
import POM.Invoice.CommodityMeasures;
import POM.Invoice.InvoiceInputs;
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
import org.openqa.selenium.WebElement;

import static POM.LoginPage.loginIntoApp;
import static POM.Sidebar.ApplicationPages.NEW_INVOICE;
import static POM.Sidebar.SidebarModule.goToPage;

@RunWith(JUnitParamsRunner.class)
public class CommodityInputsTest {

    private static WebDriver driver;
    private static Commodity commodity;

    @BeforeClass
    public static void setupAll() {
        ChromeDriverSetup chromeDriverSetup = new ChromeDriverSetup("http://localhost:3000/");
        driver = chromeDriverSetup.getWebDriver();

        loginIntoApp(driver, "a", "a");
        goToPage(driver, chromeDriverSetup.getWebDriverWait(), NEW_INVOICE);
        commodity = new Commodity(driver);
        commodity.addEmptyCommodity();

    }

    @AfterClass
    public static void clean() {
        driver.quit();
    }

    private Object[] measureParams(){
       return CommodityMeasures.values();
    }

    @Test
    @Parameters(method = "measureParams")
    public void setMeasureInNewCommodity(CommodityMeasures measure) throws InterruptedException {
        commodity.setMeasure(measure);
        String xpathString = "//*[text() = '" + measure.getMeasure() + "']";
        driver.findElement(By.xpath(xpathString));
    }

    private Object[] netPriceParameters(){
        return new Object[]{
                new Object[]{"1","1.00"},
                new Object[]{"3,1","3.10"},
                new Object[]{"5.15","5.15"}
        };
    }

    @Test
    @Parameters(method = "netPriceParameters")
    public void setNetPriceInNewCommodity(String input, String expected) {
        WebElement netPrice = commodity.setInputValue(InvoiceInputs.NET_PRICE, input,0);
        Assert.assertEquals(expected, netPrice.getAttribute("value"));
    }

    private Object[]discountParameters(){
        return new Object[]{
                new Object[]{"1","1"},
                new Object[]{"3,1","3.1"},
                new Object[]{"5.15","5.15"}
        };
    }

    @Test
    @Parameters(method = "discountParameters")
    public void setDiscountInNewCommodity(String input, String expected) {
        WebElement discount = commodity.setInputValue(InvoiceInputs.DISCOUNT, input,0);
        Assert.assertEquals(expected, discount.getAttribute("value"));

    }

    private Object[] taxValueParams(){
        return TaxRate.values();
    }

    @Test
    @Parameters(method = "taxValueParams")
    public void setTaxValueInNewCommodity(TaxRate taxRate) throws InterruptedException {
        commodity.setTaxRate(taxRate);
        String xpathString = "//*[text() = '" + taxRate.getTaxRate() + "']";
        driver.findElement(By.xpath(xpathString));

    }
}
