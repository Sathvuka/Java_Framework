package headspin.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import java.io.IOException;

import static headspin.implementation.AppAccess.*;

public class StepsTestInitializer {

       @Before
       public void start() throws IOException {
            System.out.println("App Launching");
           // AccessApplication();

        }

        @After
        public void end() throws IOException {
            teardown();
            System.out.println("App terminated");
        }

}
