package com.ss.tests;

import com.ss.data.Constants;
import com.ss.pages.MainPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class FilterByPriceTest extends TestBase {

    private MainPage mainPage;
    private SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void changePageLang() {
        mainPage = new MainPage(driver);
        mainPage.header.changeLang();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.ss.com/ru/", "Page is not translated to the Russian language");
        softAssert.assertEquals(mainPage.header.getLangButtonText(), "LV", "Text for language button is not changed");

    }

    @Test(description = "Apply filter by price and verify result")
    public void filterByPriceTest() {
        mainPage.clickOnGroupHeader(Constants.CATEGORY_HEADER_TRANSPORT_RUS)
                .clickOnCategory("Легковые авто")
                .filters.setMinPrice(1000)
                .filters.setMaxPrice(2000)
                .filters.clickOnSearchButton()
                .verifyPriceRange()
                .table.verifyColumnDataContainsInfo(Constants.FILTER_PRICE_RUS, "€");
    }

    public void openTargetPage() {
        driver.get(baseUrl);
    }
}
