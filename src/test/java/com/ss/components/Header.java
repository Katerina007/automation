package com.ss.components;

import com.ss.pages.Page;
import com.ss.pages.MainPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class Header extends Page {

    @FindBy(css = "div.page_header span.page_header_head")
    private WebElement headerLogo;

    @FindBy(css = "div.page_header span.page_header_menu b a")
    private List<WebElement> headerMenuItem;

    @FindBy(css = "span.menu_lang")
    private WebElement languageButton;

    @Step("Click on header logo")
    public MainPage clickHeaderLogo() {
        headerLogo.click();
        logger.info("Click on header logo");
        return new MainPage(driver);
    }

    @Step("Get header menu")
    public List<String> getHeaderMenu() {
        List<String> items = headerMenuItem.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        logger.info("Header categories are: " + items.stream().collect(Collectors.joining("\n")));
        return items;
    }

    @Step("Change language")
    public void changeLang() {
        logger.info("Change language");
        languageButton.click();
    }

    @Step("Get language button text")
    public String getLangButtonText() {
        logger.info("Language button text is: " + languageButton.getText());
        return languageButton.getText();
    }

    public Header(WebDriver driver) {
        super(driver);
    }
}
