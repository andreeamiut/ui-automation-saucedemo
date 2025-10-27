package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginBtn = By.id("login-button");
    private final By error = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) { this.driver = driver; }

    public LoginPage open() {
        driver.get("https://www.saucedemo.com/");
        return this;
    }

    public LoginPage typeUsername(String user) {
        driver.findElement(username).clear();
        driver.findElement(username).sendKeys(user);
        return this;
    }

    public LoginPage typePassword(String pass) {
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);
        return this;
    }

    public void submit() { driver.findElement(loginBtn).click(); }

    public String getError() { return driver.findElement(error).getText(); }
}
