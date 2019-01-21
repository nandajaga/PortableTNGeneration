package com.ctl.it.qa.yourapplication.tools.pages.common;

import com.ctl.it.qa.yourapplication.tools.pages.yourApplicationPage;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class yourApplicationHomePage extends yourApplicationPage {

	@FindBy(xpath = ".//*[@id='hM_6aa3dbf9faa751837e4b9c104b9060a0']")
	public WebElementFacade tab_home;

	@Override
	public WebElementFacade getUniqueElementInPage() {
		return tab_home;
	}
}
