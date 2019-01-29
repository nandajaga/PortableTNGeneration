package com.ctl.it.qa.yourapplication.tests;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.ctl.it.qa.staf.Environment;
import com.ctl.it.qa.staf.STAFEnvironment;
import com.ctl.it.qa.staf.Steps;
import com.ctl.it.qa.staf.TestEnvironment;
import com.ctl.it.qa.staf.SplunkLogger;
import cucumber.api.CucumberOptions;

@TestEnvironment(Environment.ITV1)
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    
		plugin = { 
		        	"json:target/cucumber.json", 
		        	"pretty:target/cucumber-pretty.txt",
		        	"usage:target/cucumber-usage.json", 
		        	"junit:target/cucumber-results.xml",
		        	"html:target/cucumber-report/",
		        	"rerun:target/rerun-failed-scenarios/rerun.txt"
		         },
		
		features="src/test/resources/features/",
		
		monochrome = true,
	
		tags={"@10000TNs"}
		
		        )

public class YourApplicationTestRunner {
	
	@BeforeClass
	public static void setEnvironment() {
		STAFEnvironment.registerEnvironment(YourApplicationTestRunner.class);
		Steps.initialize("YourApplication.xml");
		SplunkLogger.cukeTestSplunkBeforeClass();
	}
	
	@AfterClass
	public static void generateSplunkLog(){
		SplunkLogger.cukeTestSplunkAfterClass(YourApplicationTestRunner.class);
	}

}