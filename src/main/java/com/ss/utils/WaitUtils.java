package com.ss.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.testng.Assert.fail;

public abstract class WaitUtils {

    private static final int WAIT_TIME_OUT = 30;
    private static final int POLING_TIME = 50;

    private static WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);

    /**
     * Wait for page fully loads
     * @param driver WebDriver
     * @param pageClass Class of initialized page (for logger)
     * @param <T> Class
     */
    public static <T> void forPageLoaded(WebDriver driver, T pageClass) {
        ExpectedCondition<Boolean> condition = dvr -> {
            assert dvr != null;
            return ((JavascriptExecutor) dvr)
                    .executeScript("return document.readyState")
                    .toString().equals("complete");
        };
        try {
            wait = new WebDriverWait(driver, WAIT_TIME_OUT);
            wait.pollingEvery(Duration.ofMillis(POLING_TIME));
            wait.until(condition);
        } catch (Throwable e) {
            logger.error("Page with " + pageClass + " did not load within " + WAIT_TIME_OUT + " sec");
            fail("Page with " + pageClass + " did not load within " + WAIT_TIME_OUT + " sec");
        }
    }

    /**
     * Wait for dropdown appeared
     * @param driver WebDriver
     * @param dropdown WebElement dropdown
     */
    public static void forDropDownAppeared(WebDriver driver, WebElement dropdown) {
        wait = new WebDriverWait(driver, WAIT_TIME_OUT);
        wait.pollingEvery(Duration.ofMillis(POLING_TIME));
        wait.until(ExpectedConditions.visibilityOf(dropdown));
        logger.info("Dropdown appeared");
    }

    /**
     * Wait for dropdown disappeared
     * @param driver WebDriver
     * @param dropdown dropdown
     */
    public static void forDropDownDisappeared(WebDriver driver, WebElement dropdown) {
        wait = new WebDriverWait(driver, WAIT_TIME_OUT);
        wait.pollingEvery(Duration.ofMillis(POLING_TIME));
        wait.until(ExpectedConditions.invisibilityOf(dropdown));
        logger.info("Dropdown disappeared");
    }
}
