package com.ss.components;

import com.ss.pages.Page;
import com.ss.pages.ProductPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Filters extends Page {

    @FindBy(css = "#today_cnt_sl")
    private WebElement daysDropdown;

    @FindBy(css = "#today_cnt_sl option")
    private List<WebElement> dayDropdownOptions;

    @FindBy(css = "*[class^='filter_']:not([id*='f_o']):not([class*='second']):not([class*='sel'])")
    private List<WebElement> filterList;

    @FindBy(xpath = "//select[contains(@class, 'filter_sel')]/option[@value]")
    private List<WebElement> dropdownOptions;


    @FindBy(css = "input[id^='f_o_8']")
    private List<WebElement> priceMinMaxValue;

    @FindBy(css = "*[class='b s12']")
    private WebElement searchButton;

    public Filters(WebDriver driver) {
        super(driver);
    }

    private int getOptionIndex(String optionName) {
        List<String> optionNames = dropdownOptions.stream().map(WebElement::getText).collect(Collectors.toList());
        return optionNames.indexOf(optionName);
    }

    private int getFilterIndex(String filter) {
        return getFilterList().indexOf(filter);
    }

    private void clickOnOption(int index) {
        logger.info("Click on option:" + dropdownOptions.get(index).getText());
        dropdownOptions.get(index).click();
    }

    @Step("Get list of options of \"{0}\" filter")
    public List<String> getFilterOptions(String filter) {
        List<String> options = filterList.get(getFilterIndex(filter)).findElements(By.cssSelector("option[value]"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        logger.info("Options of \"{}\" filter:\n{}",filter,  options.stream().collect(Collectors.joining("\n")));
        return options;
    }

    @Step("Get filter list")
    public ArrayList<String> getFilterList() {
        List<String> filters = filterList.stream().map(WebElement::getText).collect(Collectors.toList());
        ArrayList<String> fltr = new ArrayList<>();
        filters.forEach(filter -> fltr.add(filter.substring(0, filter.indexOf(":"))));
        logger.info("There are filters on the page: " + fltr.stream().collect(Collectors.joining("\n")));
        return fltr;
    }

    @Step("Input min price: {0}")
    public ProductPage setMinPrice(Integer minPrice) {
        logger.info("Clear the min price input field");
        priceMinMaxValue.get(0).clear();
        logger.info("Input min price: " + minPrice);
        priceMinMaxValue.get(0).sendKeys(minPrice.toString());
        return new ProductPage(driver);
    }

    @Step("Input max price: {0}")
    public ProductPage setMaxPrice(Integer maxPrice) {
        logger.info("Clear the max price input field");
        priceMinMaxValue.get(1).clear();
        logger.info("Input min price: " + maxPrice);
        priceMinMaxValue.get(1).sendKeys(maxPrice.toString());
        return new ProductPage(driver);
    }

    @Step("Get min price from filter")
    public int getMinPriceFromFilter() {
        int min = parseInt(priceMinMaxValue.get(0).getAttribute("value"));
        logger.info("Min price in filter is: " + min);
        return min;
    }

    @Step("Get max price from filter")
    public int getMaxPriceFromFilter() {
        int max = parseInt(priceMinMaxValue.get(1).getAttribute("value"));
        logger.info("Max price in filter is: " + (max));
        return max;
    }

    @Step("Click on Search button")
    public ProductPage clickOnSearchButton() {
        logger.info("Click Search button");
        searchButton.click();
        return new ProductPage(driver);
    }

    @Step("Get Search button text")
    public String getSearchButtonText() {
        logger.info("Get Search button text ");
        return searchButton.getAttribute("value");
    }

    @Step("Verify that for the \"{0}\" filter the following option ia set: {1}")
    public ProductPage verifySelectedOptionForFilter(String filterName, String option) {
        SoftAssert softAssert = new SoftAssert();
        String selectedOption = filterList.get(getFilterIndex(filterName))
                .findElement(By.cssSelector("option[value][selected]"))
                .getText();
        logger.info("\"{}\" option is selected for \"{}\" filter", selectedOption, filterName);
        softAssert.assertTrue(selectedOption.equals(option + " "));
        return new ProductPage(driver);
    }

}
