package com.ctl.it.qa.yourapplication.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.ctl.it.qa.yourapplication.tools.steps.YourApplicationSteps;
import com.ctl.it.qa.staf.Page;
import com.ctl.it.qa.staf.SplunkLogger;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;


public class StepDefinition {
	
	@Steps
	YourApplicationSteps YourApplicationUserSteps;

	
	@Before
	public void beforeEveryScenario(Scenario scenario) throws IOException {

		SplunkLogger.stepDefinitionSplunkBefore(StepDefinition.class,scenario);		
		
	}
	
	@After
	public void afterEveryScenario(Scenario scenario) {
		SplunkLogger.stepDefinitionSplunkAfter(StepDefinition.class,scenario);
		com.ctl.it.qa.staf.Steps.isInitialized = false;
		Page.isInitialized = false;
		scenario.write("Data used for this test case:" + "\r\n");
		Path currentPath = Paths.get(System.getProperty("user.dir"));
		Path validationErrorPath = Paths.get(currentPath.toString(), "target", "ValidationErrorScreenshot.jpg");
		printData(scenario);
		if (scenario.isFailed()) {			
			File src= ((TakesScreenshot)YourApplicationUserSteps.getDriver()).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(src, new File(validationErrorPath.toString()));
				scenario.embed(((TakesScreenshot)YourApplicationUserSteps.getDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
			}

			catch (IOException e)
			{
				System.out.println(e.getMessage());

			}
		}
		
		try
        {
            System.out.println("Checking for Driver existense, if it exists closing it.");
        }
		
        finally
        {
            if (YourApplicationUserSteps.getDriver() != null)
            {
            	YourApplicationUserSteps.getDriver().close();
            	YourApplicationUserSteps.getDriver().quit();

            }
        }
		
	}
	
    private void printData(Scenario scenario) {
    	

        if (Serenity.sessionVariableCalled("product_name") != null) {
                        scenario.write("Product Name : " + Serenity.sessionVariableCalled("product_name").toString());
        }
        
        if (Serenity.sessionVariableCalled("request_id") != null) {
                        scenario.write("Request ID : " + Serenity.sessionVariableCalled("request_id").toString());
        }
        

}
}
