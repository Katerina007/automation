package com.ss.listeners;

import com.ss.tests.TestBase;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


public class Listener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenShot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        takeScreenShot(result);
    }

    @Override
    public void onConfigurationFailure(ITestResult result) {
        takeScreenShot(result);
    }

    @Attachment(value = "Failed test screenshot", type = "image/png")
    private byte[] saveScreenShot(byte[] screenshot) {
        return screenshot;
    }

    private void takeScreenShot(ITestResult result){
        if (result.getMethod().getGroups().length == 0) {
            Object currentClass = result.getInstance();
            WebDriver driver = ((TestBase) currentClass).getDriver();
            byte[] srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            saveScreenShot(srcFile);
        }
    }
}
