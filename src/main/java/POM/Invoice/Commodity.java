package POM.Invoice;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Commodity {

    private WebDriver driver;

    public Commodity(WebDriver driver) {
        this.driver = driver;
    }

    public void addEmptyCommodity() {
        driver.findElement(By.id("addEmptyCommodity")).click();
    }

    public void addCommodityFromSelector(WebDriverWait wait, int optionNumber) throws InterruptedException {
        String xpathString = "//*[contains(@id,'option-" + optionNumber + "')]";
        WebElement selector = driver.findElement(By.id("commoditySelect"));
        selector.click();
        wait.until(e -> selector.findElement(By.xpath(xpathString))).click();
        driver.findElement(By.id("addFromSelectOption")).click();
    }

    public WebElement setInputValue(InvoiceInputs input, String value, int rowNumber) {
        WebElement element = driver.findElements(By.name(input.getInput())).get(rowNumber);
        deleteValueInInput(element);
        element.sendKeys(value);
        mouseClickOnEmpty(driver);

        return element;
    }

    public void setMeasure(CommodityMeasures measure) throws InterruptedException {
        String xpathString = "//*[text() = '" + measure.getMeasure() + "']";
        driver.findElement(By.id("measureSelect")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath(xpathString)).click();
    }

    public void setTaxRate(TaxRate taxRate) throws InterruptedException {
        String xpathString = "//*[text() = '" + taxRate.getTaxRate() + "']";
        driver.findElement(By.id("vatSelectOptions")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath(xpathString)).click();
    }

    private void deleteValueInInput(WebElement input) {
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
    }

    private void mouseClickOnEmpty(WebDriver driver) {
        new Actions(driver).moveToElement(
                driver.findElement(By.id("counterRow"))
        ).click().perform();
    }

}
