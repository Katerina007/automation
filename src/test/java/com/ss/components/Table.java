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

public class Table extends Page {

    @FindBy(css = "tr[id='head_line'] td")
    private List<WebElement> tableColumns;

    @FindBy(css = "tr[id]:not([id='head_line']):not([id='tr_bnr_712'])")
    private List<WebElement> tableRows;

    @FindBy(css = "div.d1 a")
    private List<WebElement> postDescriptionList;

    @FindBy(css = "div.ads_region")
    private List<WebElement> postPlaceList;

    public Table(WebDriver driver) {
        super(driver);
    }

    @Step("Get list of column titles")
    public List<String> getColumnTitles () {
        List<String> columnTitles = tableColumns.stream().map(WebElement::getText).collect(Collectors.toList());
        List<String> columns = new ArrayList<>();
        columnTitles.forEach(p -> columns.add(p.trim().replace("\n", " ")));
        logger.info("Column titlesare:\n" + columns.stream().collect(Collectors.joining("\n")));
        return columns;
    }

    private int getColumnIndex(String columnName) {
        logger.info("Get \"{}\" column index", columnName);
        List<String> columns = tableColumns.stream().map(WebElement::getText).collect(Collectors.toList());
        return columns.indexOf(columnName);
    }

    @Step("Get list of values from \"{0}\" column")
    public List<String> getColumnValues(String columnName) {
        logger.info("Get values of all rows from column named: " + columnName);
        List<String> values = new ArrayList<>();
        int columnIndex = getColumnIndex(columnName)+2;
        tableRows.forEach(row -> values.add(row.
                        findElements(By.cssSelector("td"))
                        .get(columnIndex)
                        .getText()));
        return values;
    }

    @Step("Verify that values of \"{0}\" column contain \"{1}\"")
    public ProductPage verifyColumnDataContainsInfo(String columnName, String info) {
        logger.info("Verify that values of \"{}\" column contain \"{}\"", columnName, info);
        SoftAssert softAssert = new SoftAssert();
        getColumnValues(columnName).forEach(value -> softAssert.assertTrue(value.contains(info)));
        softAssert.assertAll();
        return new ProductPage(driver);
    }

    @Step("Verify that \"{0}\"column has any value")
    public ProductPage verifyColumnDataContainsInfo(String columnName) {
        logger.info("Verify that \"{}\" column has any value", columnName);
        SoftAssert softAssert = new SoftAssert();
        getColumnValues(columnName).forEach(value -> softAssert.assertFalse(value.equals("")));
        softAssert.assertAll();
        return new ProductPage(driver);
    }

    @Step("Verify that values of \"{0}\" column contain any of the list value: \"{1}\"")
    public ProductPage verifyColumnDataContainsInfo(String columnName, List<String> info) {
        logger.info("Verify that each row in \"{}\" contains any of the list value: {}", columnName, info);
        SoftAssert softAssert = new SoftAssert();
        boolean result = false;
        List<String> values = getColumnValues(columnName);
        for (int i =  0; i < values.size(); i++) {
            for (int j = 0; j < info.size(); j++) {
                if (values.get(i).contains(info.get(j))){
                    result = true;
                    break;
                }
            }
            softAssert.assertTrue(result);
        }
        softAssert.assertAll();
        return new ProductPage(driver);
    }

    @Step("Verify that post description has any value")
    public ProductPage verifyPostDescriptionData() {
        logger.info("Verify that post description has any value");
        SoftAssert softAssert = new SoftAssert();
        postDescriptionList.stream().map(WebElement::getText).forEach(p -> softAssert.assertFalse(p.equals("")));
        softAssert.assertAll();
        return new ProductPage(driver);
    }

    @Step("Verify that post place info has any value")
    public ProductPage verifyPostPlaceData() {
        logger.info("Verify that post place info has any value");
        SoftAssert softAssert = new SoftAssert();
        postPlaceList.stream().map(WebElement::getText).forEach(p -> softAssert.assertFalse(p.equals(""), "Place info is not expected" ));
        System.out.println(postPlaceList.stream().map(WebElement::getText).collect(Collectors.toList()));
        softAssert.assertAll();
        return new ProductPage(driver);
    }

    @Step("Verify that thumbnails are displayed for each row")
    public ProductPage verifyThumbnails() {
        logger.info("Verify that thumbnails are displayed for each row");
        SoftAssert softAssert = new SoftAssert();
        tableRows.forEach(p -> softAssert.assertFalse(p.findElement(By.tagName("img")).getAttribute("src").equals(""), "src attribute is empty in " + tableRows.indexOf(p) + "line"));
        return new ProductPage(driver);
    }

    @Step("Verify that checkboxes are displayed for each row")
    public ProductPage verifyCheckBoxes() {
        logger.info("Verify that checkboxes are displayed for each row");
        SoftAssert softAssert = new SoftAssert();
        tableRows.forEach(p -> softAssert.assertTrue(p.findElement(By.cssSelector("input[type=checkbox]")).isDisplayed()));
        softAssert.assertAll();
        return new ProductPage(driver);
    }
}
