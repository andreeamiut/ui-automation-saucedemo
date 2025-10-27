# ui-automation-saucedemo

## Running tests with a local Selenium container

This project supports running tests against a remote Selenium endpoint. To run tests locally using Docker:

1. Start a Selenium standalone Chrome container:

```bash
docker run -d --rm --shm-size=2g -p 4444:4444 selenium/standalone-chrome:latest
```

2. Run Maven tests pointing to the Selenium URL:

```bash
SELENIUM_REMOTE_URL=http://localhost:4444 wd=/wd/hub mvn -DskipTests=false test
```

Note: this repository's `BasePage` checks the `SELENIUM_REMOTE_URL` (or `REMOTE_WEBDRIVER_URL`) environment variable and will use a `RemoteWebDriver` if set. If you run in CI, set that variable to your grid or cloud provider URL.

If Docker isn't available in your environment, use a cloud testing service (Sauce Labs, BrowserStack, etc.) or ensure Chrome and the necessary system libraries are present in your runner image.