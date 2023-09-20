package headspin.basicFunctionalities;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static headspin.globalVariables.GlobalVariables.Appium_Driver;
import static headspin.globalVariables.GlobalVariables.wait;

public class WaitFunctions {
    public static WebDriverWait webdriverWait() {
        wait = new WebDriverWait(Appium_Driver, Duration.ofSeconds(30));
        return wait;
    }

    /**
     * =========================================================================================
     * Function Name: waitForElementPresent
     * Description: This function is used to Wait till element is visible in the current page.
     * Usage: waitForElementAndClick(btn_login);
     * ===========================================================================================
     */

    public static boolean waitForElementPresent(By elementToWaitFor) {
        try
        {
            webdriverWait().until(ExpectedConditions.presenceOfElementLocated(elementToWaitFor));
            return true;
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            System.out.println("No Such Element Present");
            return false;
        }

    }

    /**=========================================================================================
     * Function Name: waitForElementPresentAndClick
     * Description: This function is used to Wait till element is visible in the current page.
     * Usage: waitForElementAndClick(btn_login);
     *===========================================================================================*/

    public static void waitForElementAndClick(By elementToWaitFor) {
        try
        {
            webdriverWait().until(ExpectedConditions.presenceOfElementLocated(elementToWaitFor)).click();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            System.out.println("No Such Element Present");
        }
    }

    /**=========================================================================================
     * Function Name: waitForElementPresentAndSendKeys
     * Description: This function is used to Wait till element is visible in the current page.
     * Usage: waitForElementAndSendKeys(btn_login, "username");
     *===========================================================================================*/

    public static void waitForElementAndSendKeys(By elementToWaitFor, String message) {
        try
        {
            webdriverWait().until(ExpectedConditions.presenceOfElementLocated(elementToWaitFor)).sendKeys(message);
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            System.out.println("No Such Element Present");
        }
    }

    /**
     * =========================================================================================
     * Function Name: waitForElementPresentAndGetText
     * Description: This function is used to Wait till element is visible in the current page.
     * Usage: waitForElementAndGetText(txt_message);
     * ===========================================================================================
     */

    public static String waitForElementAndGetText(By elementToWaitFor) {
        String output_message = null;
        try
        {
            output_message = webdriverWait().until(ExpectedConditions.presenceOfElementLocated(elementToWaitFor)).getText();
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            System.out.println("No Such Element Present");
        }
        return output_message;
    }

    /**=========================================================================================
     * Function Name: waitForElementPresentAndConfirmURL
     * Description: This function is used to Wait till element is visible in the current page.
     * Usage: waitForElementAndGetText(txt_message);
     *===========================================================================================*/

    public static void waitForElementPresentAndConfirmURL(String url) {
        try
        {
            webdriverWait().until(ExpectedConditions.urlToBe(url));
        }
        catch(Exception ex)
        {
            System.out.println(ex);
            System.out.println("URL Didn't Match");
        }
    }


    /**=========================================================================================
     * Function Name: waitForSpecificTime
     * Description: This function is used to Wait for user specified time interval.
     * Usage: waitForSpecificTime(5000);
     *===========================================================================================*/

    public static void waitForSpecificTime(long numOfSeconds) {
        try
        {
            Thread.sleep(numOfSeconds*1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            System.err.println("Exception caught while waiting for " + numOfSeconds + "seconds");
        }
    }


}
