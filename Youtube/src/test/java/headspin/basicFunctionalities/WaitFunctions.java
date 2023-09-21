package headspin.basicFunctionalities;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static headspin.globalVariables.GlobalVariables.Appium_Driver;
import static headspin.globalVariables.GlobalVariables.*;

public class WaitFunctions {
    public static WebDriverWait webdriverWait() {
        wait = new WebDriverWait(Appium_Driver, Duration.ofSeconds(30));
        return wait;
    }

    public static WebDriverWait shortwebdriverWait() {
        shortwait=new WebDriverWait(Appium_Driver, Duration.ofSeconds(3));
        return shortwait;

    }
    public static void waitForElementPresent(By elementToWaitFor) {
        webdriverWait().until(ExpectedConditions.presenceOfElementLocated(elementToWaitFor));
    }
    public static void shortWaitForElementPresent(By elementToWaitFor) {
        shortwebdriverWait().until(ExpectedConditions.presenceOfElementLocated(elementToWaitFor));
    }

    public static void waitForElementAndClick(By elementToWaitFor) {
        webdriverWait().until(ExpectedConditions.presenceOfElementLocated(elementToWaitFor)).click();
    }
    public static void waitForElementAndSendKeys(By elementToWaitFor, String message) {
        webdriverWait().until(ExpectedConditions.presenceOfElementLocated(elementToWaitFor)).sendKeys(message);
    }
    public static String waitForElementAndGetText(By elementToWaitFor) {
        String output_message = null;
        output_message = webdriverWait().until(ExpectedConditions.presenceOfElementLocated(elementToWaitFor)).getText();
        return output_message;
    }
    public static void waitForElementPresentAndConfirmURL(String url) {
        webdriverWait().until(ExpectedConditions.urlToBe(url));
    }
    public static void waitForSpecificTime(long numOfSeconds) throws InterruptedException {
        Thread.sleep(numOfSeconds*1000);
    }

}
