package io.headspin.Pages;

import org.openqa.selenium.By;

import java.time.Duration;

import static io.headspin.Pages.basepage.*;
import static io.headspin.lib.Config.*;

public class videoPage{
    public static By playvideo =By.xpath("(//android.view.ViewGroup)[5]");
    public static By ad=By.xpath("//android.widget.ImageView[@content-desc='Close ad panel']");
    public static By videoplayer=By.xpath("//android.widget.FrameLayout[@content-desc='Video player']");
    public static By sharebutton=By.xpath("//android.view.ViewGroup[@content-desc='Share']");


    public static void playvideo() throws InterruptedException {
        status="Video Page Load Failed";
        waitForElementAndClick(playvideo);
        time_stamps4.put("start", String.valueOf(System.currentTimeMillis()));
        time_stamps4.put("start_sensitivity","0.99");
        try{
            shortWaitForElementPresent(ad);
         }
        catch(Exception e) {
            waitForElementPresent(sharebutton);
        }
        time_stamps4.put("end", String.valueOf(System.currentTimeMillis()));
        time_stamps4.put("end_sensitivity", "0.975");
        kpi_labels.put("Video Load Time",time_stamps4);
        Thread.sleep(Duration.ofSeconds(5));
    }

    public static void pauseVideo(){
        waitForElementAndClick(videoplayer);
        status="Pass";
    }

}
