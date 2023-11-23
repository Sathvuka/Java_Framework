package headspin.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

import static headspin.basicFunctionalities.PropertyFileReader.getConfigProperty;
import static headspin.globalVariables.GlobalVariables.*;

public class BaseInitilizer {
    public static AppiumDriver driver;

    public static AppiumDriver getDriver(String udid , String url) throws MalformedURLException {
        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "SM-M317F");
        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Uiautomator2");
        cap.setCapability(MobileCapabilityType.UDID, udid);
        cap.setCapability("appPackage", getConfigProperty("AppPackage"));
        cap.setCapability("appActivity", getConfigProperty("AppActivity"));
        cap.setCapability("headspin:capture.video",true);
        cap.setCapability("headspin:testName",test_name);

        deviceurl = new URL(url);
        driver = new AppiumDriver(deviceurl, cap);
        return driver;
    }
}