package com.ss.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Page {

    protected WebDriver driver;
    protected final Logger logger;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.logger = LoggerFactory.getLogger(this.getClass());
        PageFactory.initElements(driver, this);
    }
}
