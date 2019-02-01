package com.ss.tests;


import com.ss.annotations.Rest;
import com.ss.listeners.Listener;
import com.ss.utils.ApiService;
import com.ss.utils.SuiteConfiguration;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.stqa.selenium.factory.WebDriverPool;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.ss.utils.WebDriverManager.setupWebDriver;

@Listeners({AllureTestNg.class, Listener.class})
public abstract class TestBase {

    protected static String baseUrl;
    protected static int port;
    protected static String basePath;
    protected static Capabilities capabilities;
    protected static ApiService service;
    protected Logger logger;
    protected WebDriver driver;

    @BeforeSuite
    public void initTestSuite() throws IOException {
        SuiteConfiguration config = new SuiteConfiguration();
        baseUrl = config.getProperty("site.url");
        port = Integer.valueOf(config.getProperty("server.port"));
        basePath = config.getProperty("server.base");
        capabilities = config.getCapabilities();
        service = new ApiService(baseUrl, port, basePath);
        setupWebDriver(config);
    }

    @BeforeClass
    public void initTestClass() {
        logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Initialize test class");
    }

    @BeforeMethod
    public void prepareForTestMethod(Method method) {
        logger.info("Method name: " + method.getName());
        if (method.getAnnotation(Rest.class) == null)
            initWebDriver();
        openTargetPage();
    }

    public abstract void openTargetPage();

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (!WebDriverPool.DEFAULT.isEmpty()) {
            logger.info("Close WebDriver");
            WebDriverPool.DEFAULT.dismissAll();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    private void initWebDriver() {
        driver = WebDriverPool.DEFAULT.getDriver(capabilities);
        driver.manage().window().maximize();
        logger.info("WebDriver initialized");
    }
}
