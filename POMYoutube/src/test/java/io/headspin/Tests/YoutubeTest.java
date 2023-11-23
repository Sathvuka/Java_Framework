package io.headspin.Tests;

import io.headspin.Pages.*;
import org.testng.annotations.Test;

import io.headspin.Pages.basepage;
import io.headspin.Pages.homePage;
import io.headspin.Pages.searchPage;
import io.headspin.Pages.videoPage;

public class YoutubeTest {
      public static String test_name="Youtube_POM_Java";
      @Test
      public void Test1() throws InterruptedException {
          basepage.startapp();
          homePage.verify_homepage();
          homePage.serchtab();
          searchPage.searchtab("headspin performance session video");
          videoPage.playvideo();
          videoPage.pauseVideo();
      }

}
