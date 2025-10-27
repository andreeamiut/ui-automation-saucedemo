package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
    private final WebDriver driver;
    private final By title = By.cssSelector(".title");

    public InventoryPage(WebDriver driver) { this.driver = driver; }

    public String getTitle() { return driver.findElement(title).getText(); }
}
