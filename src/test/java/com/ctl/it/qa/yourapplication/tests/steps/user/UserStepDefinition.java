package com.ctl.it.qa.yourapplication.tests.steps.user;

import net.thucydides.core.annotations.Steps;

import java.io.IOException;

import com.ctl.it.qa.yourapplication.tools.steps.user.YourApplicationUserSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserStepDefinition {

	 @Steps
	    YourApplicationUserSteps endUser;

	    @Given("^I am in your application url$")
	    public void I_am_in_your_application_url() {
	        endUser.is_in_your_application_login_page();
	    }

	    @When("^I log in as a \"([^\"]*)\" user$")
	    public void I_log_in_as_a_user(String userType) {
	        endUser.logs_in_as(userType);
	    }

	    @Then("I should be on Home Page")
	    public void I_should_be_on_home_page() {
	        endUser.should_be_on_home_page();
	    }

	    @Then("I should be on Login Page")
	    public void I_should_be_on_login_page() {
	        endUser.should_be_on_login_page();
	    }
	    
	    @Then("^i need to input range of NPA and NXX and search$")
	    public void i_need_to_input_range_of_NPA_and_NXX_and_search() throws IOException, InterruptedException {
	     endUser.NPA_NXX_values_and_store_the_values_in_sheet();
	    }


	    @Then("^in TAI check for the range of BTNs is Portable or not$")
	    public void in_TAI_check_for_the_range_of_BTNs_is_Portable_or_not() throws InterruptedException, IOException {
	    	endUser.TAI_login_check_PortableTN();
	    }
	    
	    @Then("^I send TN details in an email$")
	    public void i_send_TN_details_in_an_email() throws Throwable {
			endUser.SendEmailhavingOrderDetails();
	    }
}
