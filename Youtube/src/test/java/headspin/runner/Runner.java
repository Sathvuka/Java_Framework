package headspin.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        features="/Users/sathvukaadulapuram/Automation_Java/Youtube/src/test/resources/features",
        glue="headspin.steps",
        tags="@Appium"
)
public class Runner extends AbstractTestNGCucumberTests {

}
