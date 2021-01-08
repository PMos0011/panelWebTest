package CommodityTest;

import DriverSetup.ChromeDriverSetup;
import POM.Invoice.Commodity;
import POM.Invoice.InvoiceInputs;
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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static POM.LoginPage.loginIntoApp;
import static POM.Sidebar.ApplicationPages.NEW_INVOICE;
import static POM.Sidebar.SidebarModule.goToPage;

@RunWith(JUnitParamsRunner.class)
public class CommoditySummaryCalculationTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Commodity commodity;


    @BeforeClass
    public static void setupAll() throws InterruptedException {
        ChromeDriverSetup chromeDriverSetup = new ChromeDriverSetup("http://localhost:3000/");
        driver = chromeDriverSetup.getWebDriver();
        wait = chromeDriverSetup.getWebDriverWait();

        loginIntoApp(driver, "a", "a");
        goToPage(driver, wait, NEW_INVOICE);
        commodity = new Commodity(driver);

        addCommoditiesFromSelector();

    }

    @AfterClass
    public static void clean() {
        driver.quit();
    }

    @Test
    public void invoiceSummaryGross() {

        Assert.assertEquals("346.76", driver.findElement(By.name(InvoiceInputs.SUMMARY_GROSS.getInput()))
                .getAttribute("value"));
    }

    @Test
    public void invoiceSummary() {

        Assert.assertEquals("308.75", driver.findElement(By.name(InvoiceInputs.INVOICE_SUMMARY_NET.getInput()))
                .getAttribute("value"));
        Assert.assertEquals("38.01", driver.findElement(By.name(InvoiceInputs.INVOICE_SUMMARY_TAX.getInput()))
                .getAttribute("value"));
        Assert.assertEquals("346.76", driver.findElement(By.name(InvoiceInputs.INVOICE_SUMMARY_GROSS.getInput()))
                .getAttribute("value"));

    }

    private Object[] taxTableParameters(){
        return new Object[]{
                new Object[]{"23","88.75","20.41","109.16"},
                new Object[]{"8","220.00","17.60","237.60"}
        };
    }

    @Test
    @Parameters(method = "taxTableParameters")
    public void invoiceTaxTable(String taxRate, String taxRateNet, String taxRateSummary, String taxRateGross ) {

        List<WebElement> elements = driver.findElements(By.name(InvoiceInputs.TAX_RATE.getInput()));

        int taxRateRow = findTaxRateRow(elements,taxRate);


        Assert.assertEquals(taxRateNet,
                driver.findElements(By.name(InvoiceInputs.TAX_RATE_NET.getInput()))
                .get(taxRateRow)
                .getAttribute("value"));

        Assert.assertEquals(taxRateSummary,
                driver.findElements(By.name(InvoiceInputs.TAX_RATE_SUMMARY.getInput()))
                .get(taxRateRow)
                .getAttribute("value"));

        Assert.assertEquals(taxRateGross,
                driver.findElements(By.name(InvoiceInputs.TAX_RATE_GROSS.getInput()))
                .get(taxRateRow)
                .getAttribute("value"));

    }

    private static void addCommoditiesFromSelector() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            commodity.addCommodityFromSelector(wait, i);
            commodity.setInputValue(InvoiceInputs.AMOUNT, String.valueOf(i + 2), i);
        }
    }

    private int findTaxRateRow(List<WebElement> elements, String value){
        int row = -1;

        for (int i = 0; i < elements.size(); i++) {
            if(elements.get(i).getAttribute("value").equals(value)) {
                row = i;
                break;
            }
        }

        return row;
    }


}
