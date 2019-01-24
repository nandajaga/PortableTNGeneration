/**
 * 
 */
package com.ctl.it.qa.yourapplication.tools.pages.common;

import com.ctl.it.qa.yourapplication.tools.pages.yourApplicationPage;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

/**
 * @author AB54030
 *
 */
public class NumsPage extends yourApplicationPage {


	@FindBy(xpath = "//div[contains(@id,'oCMenu_blockMenu')]")
	public WebElementFacade lbl_Block;
	
	
	@FindBy(linkText = "Return To NuMS")
	public WebElementFacade lnk_ReturntoNums;
	
	@FindBy(id = "oCMenu_searchNumbers")
	public WebElementFacade lbl_SearchLERG;
	
	@FindBy(xpath = "//div[contains(@id,'logout')]")
	public WebElementFacade btn_Signout;
	
	@FindBy(name = "npa")
	public WebElementFacade tbx_NPA;
	
	@FindBy(name = "nxx")
	public WebElementFacade tbx_NXX;	
	
	@FindBy(name = "beginRange")
	public WebElementFacade tbx_beginRange;
	
	@FindBy(name = "endRange")
	public WebElementFacade tbx_endRange;
	
	
	@FindBy(name = "searchBtn")
	public WebElementFacade btn_Search;
	
	@FindBy(name = "clearBtn")
	public WebElementFacade btn_clear;
	
	@FindBy(name = "fromNo")
	public WebElementFacade tbx_beginrangeclear;
	
	@FindBy(xpath = "/html/body/table[2]/tbody/tr[2]/td/ul/li")
	public WebElementFacade lbl_noOfEntries;
	
	@FindBy(xpath = "(//td[@class='cellBaseCentered'])[4]")
	public WebElementFacade lbl_EndRange;
	
	@FindBy(xpath = "(//td[@class='cellBaseCentered'])[3]")
	public WebElementFacade lbl_BeginRange;
	
	@FindBy(xpath = "//div[contains(@id,'oCMenu_tnMenu')]")
	public WebElementFacade lbl_TelephoneNumber;

	@FindBy(xpath = "//div[contains(text(),'Search Numbers')]")
	public WebElementFacade lbl_SearchNumber;
	
	
	@Override
	public WebElementFacade getUniqueElementInPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
