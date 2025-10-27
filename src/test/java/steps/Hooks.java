package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import pages.BasePage;

public class Hooks {
    @Before
    public void before() { BasePage.start(); }

    @After
    public void after() { BasePage.stop(); }
}
