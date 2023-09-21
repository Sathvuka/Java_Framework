package headspin.implementation;

import headspin.hsAPI.session_visual_lib;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.net.MalformedURLException;

import static headspin.globalVariables.GlobalVariables.*;
import static headspin.utils.BaseInitilizer.*;

public class AppAccess {

    public static void AccessApplication(String udid,String url) throws MalformedURLException {
        if(Appium_Driver == null || Appium_Driver.toString().contains("null"))
        {
            Appium_Driver = getDriver(udid,url);
            sessionID= String.valueOf(Appium_Driver.getSessionId());
        }
    }

    public static void teardown() throws IOException {
        if (null != Appium_Driver) {
            Appium_Driver.quit();
            session_visual_lib.run_record_session_info();
        }
    }

}
