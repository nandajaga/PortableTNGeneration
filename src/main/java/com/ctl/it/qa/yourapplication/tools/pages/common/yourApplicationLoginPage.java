package com.ctl.it.qa.yourapplication.tools.pages.common;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

import com.ctl.it.qa.staf.xml.reader.IntDataContainer;
import com.ctl.it.qa.yourapplication.tools.pages.yourApplicationPage;

public class yourApplicationLoginPage extends yourApplicationPage {

	@FindBy(name = "j_username")
	public WebElementFacade tbx_username;

	@FindBy(name = "j_password")
	public WebElementFacade tbx_password;

	@FindBy(name = "submitBtn")
	public WebElementFacade btn_log_In_To_yourapplication;

	@Override
	public WebElementFacade getUniqueElementInPage() {
		return btn_log_In_To_yourapplication;
	}

	public void enterCredentials(String userType) {
		IntDataContainer dataContainer = envData.getContainer(
				this.getClass().getSimpleName()).getContainer(userType);
		tbx_username.type(dataContainer.getFieldValue("tbx_username"));
		tbx_password.type(dataContainer.getFieldValue("tbx_password"));
	}

	public void clickLogin() {
		btn_log_In_To_yourapplication.click();
	}
}