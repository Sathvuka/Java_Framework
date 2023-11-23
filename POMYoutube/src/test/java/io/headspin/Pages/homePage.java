package io.headspin.Pages;

import org.openqa.selenium.By;

import static io.headspin.Pages.basepage.*;

import static io.headspin.lib.Config.*;
import static io.headspin.Pages.searchPage.searchtab;

public class homePage {
    public static By youtubelogo= By.xpath("//android.widget.ImageView[@content-desc='YouTube']");
    public static By searchicon= By.xpath("//android.widget.ImageView[@content-desc='Search']");
          public static void verify_homepage()
          {
              waitForElementPresent(youtubelogo);
              time_stamps1.put("end", String.valueOf(System.currentTimeMillis()));
              kpi_labels.put("Launch Time",time_stamps1);
          }
          public static void serchtab()
          {
              status="Failed to Verify Search Tab";
              waitForElementAndClick(searchicon);
              time_stamps2.put("start", String.valueOf(System.currentTimeMillis()));
              time_stamps2.put("start_sensitivity","0.99");
              waitForElementPresent(searchtab);
              time_stamps2.put("end_sensitivity","0.975");
              time_stamps2.put("end", String.valueOf(System.currentTimeMillis()));
              kpi_labels.put("Search Tab Load Time",time_stamps2);

          }
}
