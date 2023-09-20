package headspin.implementation;

import headspin.hsAPI.session_visual_lib;

import java.io.IOException;
import java.net.MalformedURLException;

import static headspin.globalVariables.GlobalVariables.*;
import static headspin.utils.BaseInitilizer.getDriver;

public class AppAccess {
    public static void AccessApplication() throws MalformedURLException
    {
        if(Appium_Driver == null || Appium_Driver.toString().contains("null"))
        {
            Appium_Driver = getDriver();
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
