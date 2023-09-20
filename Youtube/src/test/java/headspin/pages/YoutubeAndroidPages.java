package headspin.pages;


import java.net.MalformedURLException;
import java.time.Duration;

import static headspin.basicFunctionalities.PropertyFileReader.getConfigProperty;
import static headspin.basicFunctionalities.WaitFunctions.*;
import static headspin.globalVariables.GlobalVariables.*;
import static headspin.implementation.AppAccess.*;
import static headspin.locators.AppiumLocator.*;

public class YoutubeAndroidPages {

    public void launchApp() throws MalformedURLException {
        status="App Launch Failed";
        time_stamps1.put("start", String.valueOf(System.currentTimeMillis()));
        AccessApplication();
       // status="Failed to Verify HomePage";
        //time_stamps1.put("start", String.valueOf(System.currentTimeMillis()));
        waitForElementPresent(youtubelogo);
        time_stamps1.put("end", String.valueOf(System.currentTimeMillis()));
        kpi_labels.put("Launch Time",time_stamps1);
    }


    public void searchVideo() {
        status="Failed to Verify Search Tab";
        waitForElementAndClick(searchicon);
        time_stamps2.put("start", String.valueOf(System.currentTimeMillis()));
        waitForElementPresent(searchtab);
        time_stamps2.put("end", String.valueOf(System.currentTimeMillis()));
        kpi_labels.put("Search Tab Load Time",time_stamps2);

        waitForElementAndSendKeys(searchtab,getConfigProperty("searchvideo"));
        waitForElementAndClick(searchvideos);
    }


    public void playVideo() throws InterruptedException {
        status="Search Failed";
        time_stamps3.put("start", String.valueOf(System.currentTimeMillis()));
        waitForElementPresent(playvideo);
        time_stamps3.put("end", String.valueOf(System.currentTimeMillis()));
        kpi_labels.put("Search Time",time_stamps3);

        waitForElementAndClick(playvideo);
        status="Video Page Load Failed";
        time_stamps4.put("start", String.valueOf(System.currentTimeMillis()));
        if (waitForElementPresent(ad) || waitForElementPresent(sharebutton))
            time_stamps4.put("end", String.valueOf(System.currentTimeMillis()));
        else
            System.err.println("Element not Found");
        kpi_labels.put("Video Load Time",time_stamps4);

        Thread.sleep(Duration.ofSeconds(5));
        waitForElementAndClick(videoplayer);
        status="Pass";
        System.out.println(kpi_labels);

    }
}
