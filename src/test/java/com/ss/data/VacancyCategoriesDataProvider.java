package com.ss.data;

import org.testng.annotations.DataProvider;

public class VacancyCategoriesDataProvider {
    @DataProvider(name = "Vacancy categories")
    public static Object[][] vacancyRus() {
        return new Object[][] {
                {"Крупье"},
                {"Актёр"},
                {"Медсестра"}
        };
    }
}
