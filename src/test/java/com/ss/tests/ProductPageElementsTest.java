package com.ss.tests;

import com.ss.data.Constants;
import com.ss.data.VacancyCategoriesDataProvider;
import com.ss.pages.MainPage;
import com.ss.pages.ProductPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;

public class ProductPageElementsTest extends TestBase {

    private MainPage mainPage;
    private ProductPage productPage;
    private SoftAssert softAssert = new SoftAssert();
    private List<String> expectedFilters = Arrays.asList(Constants.FILTER_COMPANY_RUS,
            Constants.FILTER_WORKING_DAYS_RUS,
            Constants.FILTER_WORKING_TIME_RUS,
            Constants.FILTER_PLACE_RUS,
            Constants.FILTER_SCHEDULE_RUS,
            Constants.FILTER_SPECIALITY_RUS);
    private String expectedSearchButtonText = "Искать";
    private String expectedAddButtonText = "Разместить объявление";
    private List<String> expectedColumnTitles = Arrays.asList("Объявления дата", "Компания", "Дни работы");

    @BeforeMethod
    public void changePageLang() {
        mainPage = new MainPage(driver);
        mainPage.header.changeLang();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.ss.com/ru/", "Page is not translated to the Russian language");
        softAssert.assertEquals(mainPage.header.getLangButtonText(), "LV", "Text for language button is not changed");
        softAssert.assertAll();
    }

    @Test(description = "Verify common elements for Vacancy category page",
            dataProvider = "Vacancy categories",
            dataProviderClass = VacancyCategoriesDataProvider.class)
    public void commonElementsTest(String vacancyCategory) {
        productPage = mainPage.clickOnCategoryInGroup(Constants.CATEGORY_HEADER_WORK_AND_BUSINESS_RUS, Constants.CATEGORY_VACANCY_RUS)
                .clickOnCategory(vacancyCategory);
        softAssert.assertEquals(productPage.getTitleText(), Constants.TITLE_VACANCY_RUS + " / " + vacancyCategory);
        softAssert.assertEquals(productPage.getSelectedPostsLinkText(), Constants.LINK_SELECTED_POSTS_RU, "Selected posts link text is not expected");
        softAssert.assertTrue(productPage.getAddPostButtonTitle().equals(expectedAddButtonText), "Add post button text is not expected");
        softAssert.assertTrue(productPage.getSelectedPostsLinkText().equals(Constants.LINK_SELECTED_POSTS_RU), "Selected posts link text is not expected");
        softAssert.assertTrue(productPage.getArchiveLinkText().equals(Constants.LINK_ARCHIVE_RU), "Archive link text is not expected");
        softAssert.assertAll();
    }

    @Test(description = "Verify filter elements for Vacancy category page",
            dataProvider = "Vacancy categories",
            dataProviderClass = VacancyCategoriesDataProvider.class)
    public void filterElementsTest(String vacancyCategory) {
        productPage = mainPage.clickOnCategoryInGroup(Constants.CATEGORY_HEADER_WORK_AND_BUSINESS_RUS, Constants.CATEGORY_VACANCY_RUS)
                .clickOnCategory(vacancyCategory)
                .filters.verifySelectedOptionForFilter(Constants.FILTER_SCHEDULE_RUS, "Список")
                .filters.verifySelectedOptionForFilter(Constants.FILTER_SPECIALITY_RUS, vacancyCategory);
        softAssert.assertTrue(productPage.filters.getFilterList().containsAll(expectedFilters));
        softAssert.assertTrue(productPage.filters.getFilterOptions(Constants.FILTER_WORKING_DAYS_RUS).containsAll(Constants.FILTER_WORKING_DAYS_OPTIONS_RUS),
                Constants.FILTER_WORKING_DAYS_RUS + " filter options are not expected");
        softAssert.assertTrue(Constants.FILTER_PLACE_OPTIONS_RUS.containsAll(productPage.filters.getFilterOptions(Constants.FILTER_PLACE_RUS)),
                Constants.FILTER_PLACE_RUS + " filter options are not expected");
        softAssert.assertTrue(productPage.filters.getFilterOptions(Constants.FILTER_SCHEDULE_RUS).containsAll(Constants.FILTER_SCHEDULE_OPTIONS_RUS),
                Constants.FILTER_SCHEDULE_RUS + " filter options are not expected");
        softAssert.assertAll();
    }

    @Test(description = "Verify filter elements for Vacancy category page",
            dataProvider = "Vacancy categories",
            dataProviderClass = VacancyCategoriesDataProvider.class)
    public void tableElementsTest(String vacancyCategory) {
        productPage = mainPage.clickOnCategoryInGroup(Constants.CATEGORY_HEADER_WORK_AND_BUSINESS_RUS, Constants.CATEGORY_VACANCY_RUS)
                .clickOnCategory(vacancyCategory)
                .table.verifyCheckBoxes()
                .table.verifyThumbnails()
                .table.verifyPostDescriptionData()
                .table.verifyPostPlaceData()
                .table.verifyColumnDataContainsInfo(expectedColumnTitles.get(1))
                .table.verifyColumnDataContainsInfo(expectedColumnTitles.get(2), Constants.FILTER_WORKING_DAYS_OPTIONS_RUS);
        softAssert.assertEquals(productPage.getTitleText(), Constants.TITLE_VACANCY_RUS + " / " + vacancyCategory);
        softAssert.assertEquals(productPage.filters.getSearchButtonText(), expectedSearchButtonText, "Search button text is not expected");
        softAssert.assertTrue(productPage.table.getColumnTitles().containsAll(expectedColumnTitles), "Column titles are not expected");
        softAssert.assertAll();
    }

    public void openTargetPage() {
        driver.get(baseUrl);
    }
}
