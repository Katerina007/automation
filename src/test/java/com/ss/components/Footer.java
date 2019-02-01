package com.ss.components;

import com.ss.pages.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Footer extends Page {

    @FindBy(css = "div#page_footer a")
    private List<WebElement> footerLinks;
    @FindBy(xpath = "//div[@id=\"page_footer\"]/text()[last()]")
    private WebElement copyright;

    public Footer(WebDriver driver) {
        super(driver);
    }
}
