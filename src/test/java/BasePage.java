package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class BasePage {
    protected static WebDriver driver;

    public static void start() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();

        boolean headless = System.getenv("CI") != null
            || "true".equalsIgnoreCase(System.getenv("HEADLESS"))
            // If no DISPLAY, assume non-interactive environment and run headless
            || System.getenv("DISPLAY") == null;

            if (headless) {
                try {
                    // Chrome nou (>=109)
                    options.addArguments("--headless=new");
                } catch (Exception ignored) {
                    options.addArguments("--headless");
                }

                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
                options.addArguments("--no-first-run");
                options.addArguments("--no-default-browser-check");
                // allow newer chromedriver / selenium remote origins
                options.addArguments("--remote-allow-origins=*");
            }

            // If a remote Selenium Grid/Hub is provided, use RemoteWebDriver
            String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
            if (remoteUrl == null || remoteUrl.isBlank()) {
                remoteUrl = System.getenv("REMOTE_WEBDRIVER_URL");
            }

            if (remoteUrl != null && !remoteUrl.isBlank()) {
                try {
                    // For remote sessions, avoid passing a local user-data-dir
                    driver = new RemoteWebDriver(new URL(remoteUrl), options);
                } catch (MalformedURLException e) {
                    // fallback to local Chrome if the URL is malformed
                    tryStartLocalDriverWithRetries(options);
                }
            } else {
                // local run — try to create a unique user-data-dir to avoid profile conflicts
                Path userDataDir = null;
                try {
                    userDataDir = Files.createTempDirectory("chrome-user-data-");
                    options.addArguments("--user-data-dir=" + userDataDir.toAbsolutePath().toString());
                    // Attempt to delete on exit; best-effort cleanup
                    userDataDir.toFile().deleteOnExit();
                } catch (IOException ignored) {
                    // If we can't create a temp dir, continue without explicit user-data-dir
                }

                tryStartLocalDriverWithRetries(options);
            }
            driver.manage().window().maximize();
        }
    }

    private static void tryStartLocalDriverWithRetries(ChromeOptions options) {
        try {
            // Try starting ChromeDriver with provided options
            driver = new ChromeDriver(options);
            return;
        } catch (org.openqa.selenium.SessionNotCreatedException retryEx) {
            // Retry without explicit user-data-dir in case the environment forbids it.
            ChromeOptions fallback = new ChromeOptions();
            boolean headless = System.getenv("CI") != null
                    || "true".equalsIgnoreCase(System.getenv("HEADLESS"))
                    || System.getenv("DISPLAY") == null;
            if (headless) {
                try {
                    fallback.addArguments("--headless=new");
                } catch (Exception ignored) {
                    fallback.addArguments("--headless");
                }
                fallback.addArguments("--no-sandbox");
                fallback.addArguments("--disable-dev-shm-usage");
                fallback.addArguments("--disable-gpu");
                fallback.addArguments("--disable-extensions");
                fallback.addArguments("--no-first-run");
                fallback.addArguments("--no-default-browser-check");
                fallback.addArguments("--remote-allow-origins=*");
            }
            try {
                driver = new ChromeDriver(fallback);
            } catch (Exception finalEx) {
                // If fallback fails, rethrow original to keep test failure visible with original cause
                throw retryEx;
            }
        }
    }

    public static void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
