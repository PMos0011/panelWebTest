package POM.Sidebar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SidebarModule {

    public static WebDriver goToPage(WebDriver driver, WebDriverWait wait, ApplicationPages page) {
        WebElement element = wait.until(e -> driver.findElement(By.id(page.getPage())));
        element.click();

        return driver;
    }
}
