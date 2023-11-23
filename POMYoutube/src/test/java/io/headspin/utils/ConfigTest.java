package io.headspin.utils;

import io.appium.java_client.AppiumDriver;
import io.headspin.Tests.YoutubeTest;
import io.headspin.lib.session_visual_lib;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import static io.headspin.Tests.YoutubeTest.*;
import static io.headspin.utils.ConfigVar.*;
import static io.headspin.lib.Config.deviceurl;

public class ConfigTest {
    public static AppiumDriver Appium_Driver;
    public static DesiredCapabilities desiredCapabilities;


    @BeforeTest
    @Parameters({"udid","url"})
    public void BasicInitializer(String udid , String url) throws MalformedURLException
    {
        desiredCapabilities=new DesiredCapabilities();
        desiredCapabilities.setCapability("deviceName", device_name);
        desiredCapabilities.setCapability("udid", udid);
        desiredCapabilities.setCapability("automationName",automationName);
        desiredCapabilities.setCapability("appPackage",appPackage);
        desiredCapabilities.setCapability("appActivity",activity);
        desiredCapabilities.setCapability("platformName",platformName);
        desiredCapabilities.setCapability("headspin:capture.video",video_capture);
        desiredCapabilities.setCapability("headspin:testName",test_name);

        deviceurl = new URL(url);
      //  driver = new AppiumDriver(deviceurl,desiredCapabilities);
    }

    @AfterTest
     public void teardown() throws IOException {
        if (null !=Appium_Driver) {
            Appium_Driver.quit();
            session_visual_lib.run_record_session_info();
        }
    }
}
