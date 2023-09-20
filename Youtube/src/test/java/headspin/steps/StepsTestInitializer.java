package headspin.steps;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

import static headspin.implementation.AppAccess.*;
import static headspin.globalVariables.GlobalVariables.*;


public class StepsTestInitializer {

       @Before
       public void start() throws IOException {
            deleteReportFiles();
            System.out.println("App Launching");
           // AccessApplication();

        }

        @After
        public void end() throws IOException {
            teardown();
            System.out.println("App terminated");
        }

        @AfterStep
        public void addScreenshot(Scenario scenario)
        {
           if(scenario.isFailed())
               {
                  final byte[] screenshot = ((TakesScreenshot) Appium_Driver).getScreenshotAs(OutputType.BYTES);
                  scenario.attach(screenshot, "image/png", "Failed");
                }
        }

        private void deleteReportFiles() throws IOException {
            String filepath = "Reports";
            File file = new File(filepath);
            FileUtils.deleteDirectory(file);
            file.delete();
         }

}
