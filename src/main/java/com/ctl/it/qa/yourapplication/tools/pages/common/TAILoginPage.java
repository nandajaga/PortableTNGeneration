/**
 * 
 */
package com.ctl.it.qa.yourapplication.tools.pages.common;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.ctl.it.qa.staf.xml.reader.IntDataContainer;
import com.ctl.it.qa.yourapplication.tools.pages.yourApplicationPage;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.findby.By;

/**
 * @author AB54030
 *
 */
public class TAILoginPage extends yourApplicationPage {

	@FindBy(name = "j_username")
	public WebElementFacade tbx_username;

	@FindBy(name = "j_password")
	public WebElementFacade tbx_password;

	@FindBy(xpath = "//input[@value='Login']")
	public WebElementFacade btn_log_In_To_TAI;
	
	@FindBy(name = "tn0")
	public WebElementFacade tbx_TN;

	@FindBy(id = "buttonE")
	public WebElementFacade btn_Execute;

	@FindBy(xpath = "//*[@id='mainform']/table[3]/tbody/tr[3]/td[2]")
	public WebElementFacade lbl_PortableText;

	@FindBy(xpath = "//*[@id='mainform']/table[3]/tbody/tr[3]/td[3]")
	public WebElementFacade lbl_WirelessText;

	@FindBy(xpath = "//*[@id='mainform']/table[3]/tbody/tr[3]/td[4]")
	public WebElementFacade lbl_InternalPort;

	public String BeforeXpath = "//*[@id='mainform']/table[3]/tbody/tr[";
	
	public String TelephoneNoxpath="]/td[1]";
	
	public String PortableAfterXpath = "]/td[2]";

	public String WirelessAfterXpath = "]/td[3]";

	public String InternalAfterXpath = "]/td[4]";
	
	public String CarrierNameXpath="]/td[10]";



	@Override
	public WebElementFacade getUniqueElementInPage() {
		return btn_log_In_To_TAI;
	}

	public void enterCredentials(String userType) {
		IntDataContainer dataContainer = envData.getContainer(this.getClass().getSimpleName()).getContainer(userType);
		tbx_username.type(dataContainer.getFieldValue("tbx_username"));
		tbx_password.type(dataContainer.getFieldValue("tbx_password"));
	}

	public void clickLogin() {
		btn_log_In_To_TAI.click();
	}

	
}
