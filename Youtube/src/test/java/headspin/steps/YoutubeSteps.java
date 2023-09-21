package headspin.steps;

import headspin.pages.YoutubeAndroidPages;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.MalformedURLException;


public class YoutubeSteps {
    YoutubeAndroidPages yp1=new YoutubeAndroidPages();
    @Given("Launch the Youtube App {string} and {string}")
    public void launchTheYoutubeApp(String udid,String url) throws MalformedURLException {
        yp1.launchApp(udid,url);

    }

    @When("User Click on Search to Search for an Video")
    public void userClickOnSearchToSearchForAnVideo() {
        yp1.searchVideo();
    }

    @Then("User Play the Video")
    public void userPlayTheVideo() throws InterruptedException {
        yp1.playVideo();
    }

}
