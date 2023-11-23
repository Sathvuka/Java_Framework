package io.headspin.Pages;

import org.openqa.selenium.By;

import static io.headspin.Pages.basepage.*;
import static io.headspin.lib.Config.*;
import static io.headspin.Pages.videoPage.playvideo;

public class searchPage {
    public static By searchtab=By.xpath("//android.widget.EditText[contains(@text,'Search')]");
    public static By searchvideos=By.xpath("(//android.widget.TextView[contains(@text,'headspin')])[1]");
    public static void searchtab(String searchname)
    {
        waitForElementAndSendKeys(searchtab,searchname);
        status="Search Failed";
        time_stamps3.put("start", String.valueOf(System.currentTimeMillis()));
        time_stamps3.put("segment_start","1");
        waitForElementAndClick(searchvideos);
        time_stamps3.put("start_sensitivity","0.93");
        waitForElementPresent(playvideo);
        time_stamps3.put("end", String.valueOf(System.currentTimeMillis()));
        time_stamps3.put("end_sensitivity","0.975");
        time_stamps3.put("segment_end","-1");
        kpi_labels.put("Search Time",time_stamps3);
    }
}
