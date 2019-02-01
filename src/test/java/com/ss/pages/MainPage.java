package com.ss.pages;

import com.ss.components.Footer;
import com.ss.components.Header;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class MainPage extends Page {

    @FindBy(css = "td[id^='td']")
    private List<WebElement> groupList;

    @FindBy(css = "tr td a.a1")
    private List<WebElement> groupHeaderList;

    @FindBy(css = "img.main_images")
    private List<WebElement> groupImages;

    @FindBy(css = "div.main_category")
    private List<WebElement> categoriesByGroup;

    public Header header;
    public Footer footer;

    public MainPage(WebDriver driver) {
        super(driver);
        header = new Header(driver);
        footer = new Footer(driver);
    }

    private int getGroupIndexByHeader(String header) {
        List<String> headers = groupHeaderList.stream().map(WebElement::getText).collect(Collectors.toList());
        logger.info("Index of category header \"{}\" is {}", header, headers.indexOf(header));
        return headers.indexOf(header);
    }


    @Step("Click on group header \"{}\"")
    public ProductPage clickOnGroupHeader(String header) {
        logger.info("Click on group Header named: " + header);
        for (int i = 0; i < groupHeaderList.size(); i++) {
            if (groupHeaderList.get(i).getText().equals(header)) {
                groupHeaderList.get(i).click();
                break;
            }
        }
        return new ProductPage(driver);
    }

    @Step("Get list of categories from the group with {0} index")
    public List<WebElement> getCategoriesByGroupIndex (int index) {
        List<WebElement> menuItems = groupList.get(index)
                .findElements(By.cssSelector("a[id^='mtd']"));
        logger.info("List of categories: " + menuItems.stream().map(WebElement::getText).collect(Collectors.joining("\n")));
        return menuItems;
    }

    @Step("Click on \"{0}\" category in the \"{1}\" group")
    public ProductPage clickOnCategoryInGroup(String groupHeader, String category) {
        int groupIndex = getGroupIndexByHeader(groupHeader);
        logger.info("Click on \"{}\" category in \"{}\" group", category, groupHeader);
        getCategoriesByGroupIndex(groupIndex)
                .get(getCategoriesByGroupIndex(groupIndex)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList()).indexOf(category)).click();
        return new ProductPage(driver);
    }
}
