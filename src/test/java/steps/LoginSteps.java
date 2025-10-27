package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import pages.InventoryPage;
import pages.LoginPage;
import pages.BasePage;

public class LoginSteps {
    private LoginPage login;
    private InventoryPage inventory;

    @Given("I am on the SauceDemo login page")
    public void iAmOnTheSauceDemoLoginPage() {
        login = new LoginPage(BasePage.getDriver()).open();
    }

    @When("I login with username {string} and password {string}")
    public void iLogin(String user, String pass) {
        login.typeUsername(user).typePassword(pass).submit();
        inventory = new InventoryPage(BasePage.getDriver());
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {
        Assertions.assertEquals("Products", inventory.getTitle());
    }

    @Then("I should see an error {string}")
    public void iShouldSeeAnError(String expected) {
        Assertions.assertTrue(login.getError().contains(expected));
    }
}
