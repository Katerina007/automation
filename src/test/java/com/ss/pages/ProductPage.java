package com.ss.pages;

import com.ss.components.Filters;
import com.ss.components.Footer;
import com.ss.components.Header;
import com.ss.components.Table;
import com.ss.data.Constants;
import com.ss.utils.ElementUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class ProductPage extends Page{

    @FindBy(css = "h4.category a")
    private List<WebElement> categoryList;

    @FindBy(css = "h2.headtitle")
    private WebElement title;

    @FindBy(css = "*[class='b']")
    private WebElement addPostButton;

    @FindBy(css = "a#show_selected_a")
    private WebElement selectedPostsLink;

    @FindBy(css = "div.page_main a.a9a")
    private WebElement archiveLink;

    public Header header;
    public Footer footer;
    public Filters filters;
    public Table table;

    public ProductPage(WebDriver driver) {
        super(driver);
        header = new Header(driver);
        footer = new Footer(driver);
        filters = new Filters(driver);
        table = new Table(driver);
    }

    private List<String> getCategoryList() {
        ElementUtils.isElementListVisible(categoryList);
        List<String> categories = categoryList.stream().map(WebElement::getText).collect(Collectors.toList());
        logger.info("Category list is: " + categories.stream().collect(Collectors.joining("\n")));
        return categories;
    }

    private String getCategoryNameByIndex(int index) {
        String s = categoryList.get(index).getText().substring(0, (categoryList.get(index).getText().indexOf("(") - 1));
        logger.info("Category name by index {} is {}", index, s);
        return s;
    }

    @Step("Click on category \"{0}\"")
    public ProductPage clickOnCategory(String category) {
        logger.info("Click on category  \"{}\"", category);
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getText().startsWith(category)) {
                categoryList.get(i).click();
                break;
            }
        }
        return new ProductPage(driver);
    }

    @Step("Click on category with index \"{0}\"")
    public ProductPage clickOnCategory(int index) {
        logger.info("Click on category by index: " + index);
        categoryList.get(index).click();
        return new ProductPage(driver);
    }

    @Step("Verify that price value is more than value in filter")
    public void verifyPriceMoreThan() {
        int min = filters.getMinPriceFromFilter();
        logger.info("Verify that price is more than " + min);
        SoftAssert softAssert = new SoftAssert();
        table.getColumnValues(Constants.FILTER_PRICE_RUS).forEach(value -> softAssert.assertTrue(parseInt(value.substring(0, value.indexOf(" ")).replace(",", "")) <= min));
    }

    @Step("Verify that price value is less than value in filter")
    public void verifyPriceLessThan() {
        int max = filters.getMaxPriceFromFilter();
        logger.info("Verify that price is more than " + max);
        SoftAssert softAssert = new SoftAssert();
        table.getColumnValues(Constants.FILTER_PRICE_RUS).forEach(value -> softAssert.assertTrue(parseInt(value.substring(0, value.indexOf(" ")).replace(",", "")) <= max));
    }

    @Step("Verify that price value is in range of values in filter")
    public ProductPage verifyPriceRange() {
        int max = filters.getMaxPriceFromFilter();
        SoftAssert softAssert = new SoftAssert();
        int min = filters.getMinPriceFromFilter();
        logger.info("Verify that price is less than " + max);
        logger.info("Verify that price is more than " + min);
        table.getColumnValues(Constants.FILTER_PRICE_RUS).forEach(value ->
                softAssert.assertTrue(parseInt(value.substring(0, value.indexOf(" ")).replace(",", "")) <= max));
        table.getColumnValues(Constants.FILTER_PRICE_RUS).forEach(value ->
                softAssert.assertTrue(parseInt(value.substring(0, value.indexOf(" ")).replace(",", "")) >= min));
        softAssert.assertAll();
        return this;
    }

    @Step("Get Add post button text")
    public String getAddPostButtonTitle() {
        return addPostButton.getAttribute("value");
    }

    @Step("Get Selected post link text")
    public String getSelectedPostsLinkText() {
        return selectedPostsLink.getText();
    }

    @Step("Click on Selected post link text")
    public ProductPage clickOnSelectedPostsLink() {
        logger.info("Click on Selected Posts link");
        selectedPostsLink.click();
        return new ProductPage(driver);
    }

    @Step("Get Archive link text")
    public String getArchiveLinkText() {
        return archiveLink.getText();
    }

    public ProductPage clickOnArchiveLink() {
        logger.info("Click on Archive link");
        archiveLink.click();
        return new ProductPage(driver);
    }

    @Step("Get Title text")
    public String getTitleText() {
        return title.getText();
    }
}
