package com.ss.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class ElementUtils {

    private static final Logger logger = LoggerFactory.getLogger(ElementUtils.class);

    public static void waitForVisible(WebDriver driver, WebElement webElement, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void waitForClickable(WebDriver driver, WebElement webElement, int seconds){
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static boolean isElementListVisible(List<WebElement> webElements) {
        logger.info("Verification if the list of web elements is visible");
        for (WebElement webElement : webElements) {
            if (!webElement.isDisplayed()) {
                logger.info("Not all elements from the list are visible");
                return false;
            }
        }
        return true;
    }

    public static boolean isElementClickable(WebElement webElement) {
        return webElement.isDisplayed() && webElement.isEnabled();
    }
}
