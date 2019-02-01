package com.ctl.it.qa.yourapplication.tools.steps.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ctl.it.qa.staf.xml.reader.IntDataContainer;
import com.ctl.it.qa.yourapplication.tools.pages.common.NumsPage;
import com.ctl.it.qa.yourapplication.tools.pages.common.TAILoginPage;
import com.ctl.it.qa.yourapplication.tools.pages.common.Xls_Reader;
import com.ctl.it.qa.yourapplication.tools.pages.common.yourApplicationHomePage;
import com.ctl.it.qa.yourapplication.tools.pages.common.yourApplicationLoginPage;
import com.ctl.it.qa.yourapplication.tools.steps.YourApplicationSteps;

import net.serenitybdd.core.exceptions.SerenityManagedException;
import net.thucydides.core.annotations.Step;

@SuppressWarnings("serial")
public class YourApplicationUserSteps extends YourApplicationSteps {

	yourApplicationLoginPage yourApplicationLoginPage;
	yourApplicationHomePage yourApplicationHomePage;
	NumsPage NumsPage;
	TAILoginPage TAILoginPage;
	String NUMSWindow;
	String TaiWindow;
	int loop;
	String sheetName;
	List<String> blockbeginrange;
	List<String> blockendrange; 
	String NPA;
	String NXX;
	String TN;
	String beginRange;
	String EndRange;
	String Number_of_Entries;
	List<Long> originalTNs;
	Xls_Reader reader = new Xls_Reader(System.getProperty("user.dir") + "//src//test//resources//TNData.xlsx");
	List<Integer> loop1;
	int totalTNS;
	int counttotalTNS;
	List<String> AssignedTNSentry;
	 
	

	@Step
	public void logs_in_as(String userType) {
		IntDataContainer dataContainer = envData.getContainer(yourApplicationLoginPage.getClass().getSimpleName())
				.getContainer(userType);
		fillFields(yourApplicationLoginPage, dataContainer.getMandatoryFieldsFromAllContainers());
		yourApplicationLoginPage.clickLogin();
	}

	public void is_in_your_application_login_page() {
		yourApplicationLoginPage.openAt(envData.getFieldValue("url-NUMS"));
		yourApplicationLoginPage.maximize();
		yourApplicationLoginPage.shouldExist(yourApplicationLoginPage);
	}

	@Step
	public void should_be_on_home_page() {
		yourApplicationHomePage.shouldExist(yourApplicationHomePage);
	}

	@Step
	public void should_be_on_login_page() {
		yourApplicationLoginPage.shouldExist(yourApplicationLoginPage);
	}
	
	public void search() throws InterruptedException {
		
		
		Actions action = new Actions(getDriver());
		action.moveToElement(NumsPage.lbl_Block).build().perform();
		NumsPage.lbl_SearchLERG.click();
		waitABit(2000);
		getDriver().switchTo().frame("main_frame");
		NumsPage.btn_clear.click();
		System.out.println("Enter NPA value :");
		Random generator = new Random();
		// generate a random integer from 0 to 899, then add 100
		int randomNPA=generator.nextInt(900) + 100;
		//NPA=Integer.toString(randomNPA);
		NPA="714";
		System.out.println("NPA value :"+NPA);
		NumsPage.tbx_NPA.clear();
		NumsPage.tbx_NPA.sendKeys(NPA);
		
		System.out.println("Enter NXX value :");
		int randomNXX=generator.nextInt(900) + 100;
		NXX="412";
		//NXX=Integer.toString(randomNXX);
		System.out.println("NXX value :"+NXX);
		NumsPage.tbx_NXX.clear();
		NumsPage.tbx_NXX.sendKeys(NXX);

		NumsPage.tbx_beginrangeclear.clear();
		NumsPage.btn_Search.click();
		Thread.sleep(3000);
		Number_of_Entries=NumsPage.lbl_noOfEntries.getText();
		getDriver().switchTo().defaultContent();
	}
	public void NPA_NXX_values_and_store_the_values_in_sheet() throws IOException, InterruptedException {
		search();
		while(true) {
		if (Number_of_Entries.equals("0 entries found")) {
			System.out.println(
					"Number of entries found for the entered NPA NXX is : " + Number_of_Entries);
			System.out.println("Try again with other values of NPA and NXX");
			search();
		}else {
			break;
		}
		}
		getDriver().switchTo().frame("main_frame");
		List<WebElement> row = getDriver().findElements(By.xpath("//table[@class='tableBase']//tbody//tr"));

		String before_xpath = "//table[@class='tableBase']//tbody//tr[";
		String after_xpath = "]//td[4]";

		String after_xpath1 = "]//td[5]";
		System.out.println("Total range of values=>" + row.size());
		blockbeginrange = new ArrayList<>();
		blockendrange = new ArrayList<>();
		loop1=new ArrayList<>();
		for (int i = 1; i <= row.size(); i++) {

			beginRange = getDriver().findElement(By.xpath(before_xpath + i + after_xpath)).getText();
			System.out.println(i + "_" + "beginRange: " + beginRange);

			 EndRange = getDriver().findElement(By.xpath(before_xpath + i + after_xpath1)).getText();
			System.out.println(i + "_" + "endRange: " + EndRange);
			loop = Integer.parseInt(EndRange) - Integer.parseInt(beginRange);		
			if(loop==999) {
			loop1.add(loop);
			System.out.println("TOtal Telephone numbers will be :" +loop);
			String startRangePH = NPA + NXX + beginRange;
			String EndRangePH = NPA + NXX + EndRange;

			System.out.println(i + "_" + "StartRange :" + startRangePH);
			System.out.println(i + "_" + "EndRange :" + EndRangePH);
			blockbeginrange.add(startRangePH);
			blockendrange.add(EndRangePH);
			}
		}
		NUMSWindow = getDriver().getWindowHandle();
		searchAssignedTNs(); 
		ThousandTNS();
	}
	
	public void NPA_NXX_values_and_store_the_values_in_sheet_10000TNS() throws IOException, InterruptedException {
		search();
		while(true) {
		if (Number_of_Entries.equals("0 entries found")) {
			System.out.println(
					"Number of entries found for the entered NPA NXX is : " + Number_of_Entries);
			System.out.println("Try again with other values of NPA and NXX");
			search();
		}else {
			break;
		}
		}
		getDriver().switchTo().frame("main_frame");
		List<WebElement> row = getDriver().findElements(By.xpath("//table[@class='tableBase']//tbody//tr"));

		String before_xpath = "//table[@class='tableBase']//tbody//tr[";
		String after_xpath = "]//td[4]";

		String after_xpath1 = "]//td[5]";
		System.out.println("Total range of values=>" + row.size());
		blockbeginrange = new ArrayList<>();
		blockendrange = new ArrayList<>();
		loop1=new ArrayList<>();
		for (int i = 1; i <= row.size(); i++) {

			beginRange = getDriver().findElement(By.xpath(before_xpath + i + after_xpath)).getText();
			System.out.println(i + "_" + "beginRange: " + beginRange);

			 EndRange = getDriver().findElement(By.xpath(before_xpath + i + after_xpath1)).getText();
			System.out.println(i + "_" + "endRange: " + EndRange);
			loop = Integer.parseInt(EndRange) - Integer.parseInt(beginRange);		
			if(loop==9999) {
			loop1.add(loop);
			System.out.println("TOtal Telephone numbers will be :" +loop);
			String startRangePH = NPA + NXX + beginRange;
			String EndRangePH = NPA + NXX + EndRange;

			System.out.println(i + "_" + "StartRange :" + startRangePH);
			System.out.println(i + "_" + "EndRange :" + EndRangePH);
			blockbeginrange.add(startRangePH);
			blockendrange.add(EndRangePH);
			}
		}
		NUMSWindow = getDriver().getWindowHandle();
		searchAssignedTNs(); 
		TenThousandTNS();
	}
public void ThousandTNS() throws IOException, InterruptedException{
	int count=0;
	closeloop:
	while(count<=blockbeginrange.size()-1){
		if(loop1.get(count)==999) {
			if(AssignedTNSentry.get(count).equals("0 entries found")) {
				TN=blockbeginrange.get(count);
				loop=loop1.get(count);//to take loop value
				System.out.println(":::::::::::::"+TN+"::::::::::::::::");
				break closeloop;
			}
		}
		count++;

		}
	if(count>=blockbeginrange.size()) {
			getDriver().switchTo().defaultContent();
			NPA_NXX_values_and_store_the_values_in_sheet();
		}
	
}

public void TenThousandTNS() throws IOException, InterruptedException{
	int count=0;
	closeloop:
	while(count<=blockbeginrange.size()-1){
		if(loop1.get(count)==9999) {
			if(AssignedTNSentry.get(count).equals("0 entries found")) {
				TN=blockbeginrange.get(count);
				loop=loop1.get(count);
				System.out.println(":::::::::::::"+TN+"::::::::::::::::");
				break closeloop;
			}
		}
		count++;

		}
	if(count>=blockbeginrange.size()) {
			getDriver().switchTo().defaultContent();
			NPA_NXX_values_and_store_the_values_in_sheet_10000TNS();
		}
	
}
	
	@Step
	public void TAI_login_check_PortableTN() throws InterruptedException, IOException {
		String pageUrl = envData.getFieldValue("url-TAI");
		JavascriptExecutor jsExecutor = (JavascriptExecutor) this.getDriver();
		String jsOpenNewWindow = "window.open('" + pageUrl + "');";
		jsExecutor.executeScript(jsOpenNewWindow);
		Thread.sleep(1000);
		System.out.println("One opennd.");
		for (String handle : getDriver().getWindowHandles()) {
			getDriver().switchTo().window(handle);

		}
		IntDataContainer dataContainer = envData.getContainer(TAILoginPage.getClass().getSimpleName())
				.getContainer("Valid");
		fillFields(TAILoginPage, dataContainer.getMandatoryFieldsFromAllContainers());
		TAILoginPage.clickLogin();
		TaiWindow = getDriver().getWindowHandle();
		getDriver().findElement(By.linkText("Portability Analysis")).click();
		//checkAssignedTNs();
		setTNValues(); //calling this in checkAssignedTNs()
	}

	public void searchAssignedTNs() {
	
	int count=0;
	AssignedTNSentry=new ArrayList<>();
	while(count<=blockbeginrange.size()-1) {
	String NPA=blockbeginrange.get(count).substring(0, 3);
	String NXX=blockbeginrange.get(count).substring(3, 6);
	String beginRange=blockbeginrange.get(count).substring(blockbeginrange.get(count).length()-4);
	String EndRange=blockendrange.get(count).substring(blockendrange.get(count).length()-4);
	getDriver().switchTo().defaultContent();
	Actions action=new Actions(getDriver());
	action.moveToElement(NumsPage.lbl_TelephoneNumber).build().perform();
	NumsPage.lbl_SearchNumber.click();
	getDriver().switchTo().frame("main_frame");
	NumsPage.tbx_NPA.sendKeys(NPA);
	NumsPage.tbx_NXX.sendKeys(NXX);
	NumsPage.tbx_beginRange.sendKeys(beginRange);
	NumsPage.tbx_endRange.sendKeys(EndRange);
	NumsPage.btn_Search.click();
	AssignedTNSentry.add(NumsPage.lbl_noOfEntries.getText());
	count++;
		}
}
	
	@SuppressWarnings("unused")
	public void setTNValues() throws IOException, InterruptedException {
		originalTNs = new ArrayList<>();
		long s = Long.parseLong(TN);
		for (int row = 1; row <= loop + 1; row++) {//nnk
			//for (int row = 1; row <= 10; row++) { 

			originalTNs.add(s++);
		}
		
		/*Random generator = new Random();
		int randNo = generator.nextInt(100);
		sheetName = "TNDataList" + Integer.toString(randNo);
		System.out.println("##################" + sheetName + "##################");*/
		sheetName = "TNDataList";
		reader.removeSheet("TNDataList");
		if (!reader.isSheetExist(sheetName)) {
			reader.addSheet(sheetName);
			reader.addColumn(sheetName, "TelephoneNumber");
			reader.addColumn(sheetName, "Portable");
			reader.addColumn(sheetName, "Wireless");
			reader.addColumn(sheetName, "InternalPort");
			reader.addColumn(sheetName, "CarrierName");
			
		}
		getDriver().switchTo().window(TaiWindow);
		
		//get fist row text , check for other rows only if 1st row is Y.
		TAILoginPage.tbx_TN.clear();
		long test=originalTNs.get(0);
		TAILoginPage.tbx_TN.sendKeys(Long.toString(test));
		TAILoginPage.btn_Execute.click();
        WebElement tn=getDriver().findElement(By.xpath("//*[@id='mainform']/table[3]/tbody/tr[3]/td[2]"));
     
		if(tn.getText().equals("Y")) {
		TAILoginPage.tbx_TN.clear();
		/*for (Long s1 : originalTNs) {
			TAILoginPage.tbx_TN.sendKeys(s1 + "\n");
		}*/
		getDriver().manage().timeouts().pageLoadTimeout(900000, TimeUnit.SECONDS);

		TAILoginPage.btn_Execute.click();
	

		int c=0;
		while(c<1000) {
		if(getDriver().findElement(By.xpath("//*[@id='mainform']//table[3]//tr[3]//td[1]")).isDisplayed()) {
			break;
		}else {
			Thread.sleep(60000);
			c++;
		}
		}
		
		(new WebDriverWait(getDriver(), 9000000)).until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//*[@id='mainform']//table[3]//tr[3]//td[1]"));
			}
		});
		
		List<WebElement> Tairows1 = getDriver().findElements(By.xpath("//*[@id='mainform']//table[3]//tr"));
		int Tairows = Tairows1.size();
		System.out.println("Total Tairows =>" + Tairows);
		
		int sheetrow=2;
		for (int row = 3; row <= Tairows; row++) {
			System.out.println("******************");
			System.out.println(row);
			String actualXpath_TelephoneNo = TAILoginPage.BeforeXpath + row + TAILoginPage.TelephoneNoxpath;
			String TelePhoneNo = getDriver().findElement(By.xpath(actualXpath_TelephoneNo)).getText();
			System.out.println("Telephone Number :" + TelePhoneNo);

			String actualXpath_Portable = TAILoginPage.BeforeXpath + row + TAILoginPage.PortableAfterXpath;
			String Portable = getDriver().findElement(By.xpath(actualXpath_Portable)).getText();
			System.out.println("Portable :" + Portable);

			String actualXpath_Wireless = TAILoginPage.BeforeXpath + row + TAILoginPage.WirelessAfterXpath;
			String Wireless = getDriver().findElement(By.xpath(actualXpath_Wireless)).getText();
			System.out.println("Wireless :" + Wireless);

			String actualXpath_InternalPort = TAILoginPage.BeforeXpath + row + TAILoginPage.InternalAfterXpath;
			String InternalPort = getDriver().findElement(By.xpath(actualXpath_InternalPort)).getText();
			System.out.println("InternalPort :" + InternalPort);

			String actualXpath_carrierName = TAILoginPage.BeforeXpath + row + TAILoginPage.CarrierNameXpath;
			String CarrierName = getDriver().findElement(By.xpath(actualXpath_carrierName)).getText();
			System.out.println("Carrier Name :" + CarrierName);
			if(row==3 && Portable.equals("N")) {
				break ;
			}

			if (Portable.equals("Y") && Wireless.equals("N") && InternalPort.equals("N")) {
				reader.setCellData(sheetName, "TelephoneNumber", sheetrow, TelePhoneNo);
				reader.setCellData(sheetName, "Portable", sheetrow, Portable);
				reader.setCellData(sheetName, "Wireless", sheetrow, Wireless);
				reader.setCellData(sheetName, "InternalPort", sheetrow, InternalPort);
				reader.setCellData(sheetName, "CarrierName", sheetrow, CarrierName);
			}else {
				sheetrow=sheetrow-1;
			}
			sheetrow++;
			System.out.println("sheet row number :"+ sheetrow);
		}
		
		}
		
		System.out.println("**************************************************");
		System.out.println("Please refer the sheet for TN details with name : " + sheetName);
		totalTNS=reader.getRowCount(sheetName);
		totalTNS=totalTNS-1;
		System.out.println("Number of Portable TNs in the sheet is 1st time:: "+totalTNS);
		System.out.println("**************************************************");
		
		if(loop==999) {
			check1000TnsinSheet();
		}else if(loop==9999) {
		check10000TnsinSheet();
		}
		
	}
	
	public void check1000TnsinSheet() throws IOException, InterruptedException {
		totalTNS=reader.getRowCount(sheetName);
		totalTNS=totalTNS-1;
		counttotalTNS=totalTNS;
		int c=1;
		while(true) {
		//while(c<counttotalTNS) {
			totalTNS=reader.getRowCount(sheetName);
			totalTNS=totalTNS-1;
			counttotalTNS=totalTNS;
			//if(counttotalTNS<10) { //nnk
			if(counttotalTNS<1000) { //nnk
			getDriver().switchTo().window(NUMSWindow);
			NumsPage.btn_Signout.click();	
			getDriver().switchTo().frame("main_frame");
			try {
				waitABit(2000);
				NumsPage.lnk_ReturntoNums.click();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception occured");
			
			}
			getDriver().get(envData.getFieldValue("url-NUMS"));
			IntDataContainer dataContainer = envData.getContainer(yourApplicationLoginPage.getClass().getSimpleName())
					.getContainer("Valid");
			fillFields(yourApplicationLoginPage, dataContainer.getMandatoryFieldsFromAllContainers());
			yourApplicationLoginPage.clickLogin();
			getDriver().switchTo().defaultContent();
			NPA_NXX_values_and_store_the_values_in_sheet();
			setTNvalues_secondtime();
			}else {
				break;
			}
		
		}
		System.out.println("counttotalTNS ::::"+counttotalTNS);
		System.out.println("**************************************************");
		System.out.println("Please refer the sheet for TN details with name : " + sheetName);
		totalTNS=reader.getRowCount(sheetName);
		totalTNS=totalTNS-1;
		System.out.println("Number of Portable TNs in the sheet is:: "+totalTNS);
		System.out.println("**************************************************");
	}
	
	public void check10000TnsinSheet() throws IOException, InterruptedException {
		totalTNS=reader.getRowCount(sheetName);
		totalTNS=totalTNS-1;
		counttotalTNS=totalTNS;
		int c=1;
		while(true) {
		//while(c<counttotalTNS) {
			totalTNS=reader.getRowCount(sheetName);
			totalTNS=totalTNS-1;
			counttotalTNS=totalTNS;
		if(counttotalTNS<10000) { //nnk
			getDriver().switchTo().window(NUMSWindow);
			NumsPage.btn_Signout.click();	
			getDriver().switchTo().frame("main_frame");
			try {
				waitABit(2000);
				NumsPage.lnk_ReturntoNums.click();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception occured");
			
			}
			getDriver().get(envData.getFieldValue("url-NUMS"));
			IntDataContainer dataContainer = envData.getContainer(yourApplicationLoginPage.getClass().getSimpleName())
					.getContainer("Valid");
			fillFields(yourApplicationLoginPage, dataContainer.getMandatoryFieldsFromAllContainers());
			yourApplicationLoginPage.clickLogin();
			getDriver().switchTo().defaultContent();
			NPA_NXX_values_and_store_the_values_in_sheet_10000TNS();
			setTNvalues_secondtime();
			}else {
				break;
			}
		
		}
		System.out.println("counttotalTNS ::::"+counttotalTNS);
		System.out.println("**************************************************");
		System.out.println("Please refer the sheet for TN details with name : " + sheetName);
		totalTNS=reader.getRowCount(sheetName);
		totalTNS=totalTNS-1;
		System.out.println("Number of Portable TNs in the sheet is:: "+totalTNS);
		System.out.println("**************************************************");
	}
	
	@Step
	public void setTNvalues_secondtime() throws InterruptedException {
		originalTNs = new ArrayList<>();
		long s = Long.parseLong(TN);
		if (loop == 999) {
			//for (int row = 0; row < 10 - totalTNS; row++) { // nnk
			for (int row = 1; row <= 1000 - totalTNS; row++) { // nnk
	
				originalTNs.add(s++);
			}
		} else if (loop == 9999) {
			for (int row = 1; row <= 10000 - totalTNS; row++) { // nnk
				originalTNs.add(s++);
			}
		}
		getDriver().switchTo().window(TaiWindow);
		
		TAILoginPage.tbx_TN.clear();
		long test=originalTNs.get(0);
		TAILoginPage.tbx_TN.sendKeys(Long.toString(test));
		TAILoginPage.btn_Execute.click();
        WebElement tn=getDriver().findElement(By.xpath("//*[@id='mainform']/table[3]/tbody/tr[3]/td[2]"));
     
		if (tn.getText().equals("Y")) {
			getDriver().get(envData.getFieldValue("url-TAI"));
			TaiWindow = getDriver().getWindowHandle();
			getDriver().findElement(By.linkText("Portability Analysis")).click();
			TAILoginPage.tbx_TN.clear();

		/*for (Long s1 : originalTNs) {
			TAILoginPage.tbx_TN.sendKeys(s1 + "\n");
		}*/
	
		TAILoginPage.btn_Execute.click();
		getDriver().manage().timeouts().pageLoadTimeout(90000000, TimeUnit.SECONDS);
		int c=0;
		while(c<1000) {
		if(getDriver().findElement(By.xpath("//*[@id='mainform']//table[3]//tr[3]//td[1]")).isDisplayed()) {
			break;
		}else {
			Thread.sleep(60000);
			System.out.println("wait:::::::"+c);
			c++;
		}
		}
		(new WebDriverWait(getDriver(), 9000000)).until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.xpath("//*[@id='mainform']//table[3]//tr[3]//td[1]"));
			}
		});
		
		List<WebElement> Tairows1 = getDriver().findElements(By.xpath("//*[@id='mainform']//table[3]//tr"));
		int Tairows = Tairows1.size();
		System.out.println("Total Tairows =>" + Tairows);

		int storecount=totalTNS+2;
		for (int row =3; row <= Tairows; row++) {
			System.out.println("******************");
			System.out.println(row);
			String actualXpath_TelephoneNo = TAILoginPage.BeforeXpath + row + TAILoginPage.TelephoneNoxpath;
			String TelePhoneNo = getDriver().findElement(By.xpath(actualXpath_TelephoneNo)).getText();
			System.out.println("Telephone Number :" + TelePhoneNo);

			String actualXpath_Portable = TAILoginPage.BeforeXpath + row + TAILoginPage.PortableAfterXpath;
			String Portable = getDriver().findElement(By.xpath(actualXpath_Portable)).getText();
			System.out.println("Portable :" + Portable);

			String actualXpath_Wireless = TAILoginPage.BeforeXpath + row + TAILoginPage.WirelessAfterXpath;
			String Wireless = getDriver().findElement(By.xpath(actualXpath_Wireless)).getText();
			System.out.println("Wireless :" + Wireless);

			String actualXpath_InternalPort = TAILoginPage.BeforeXpath + row + TAILoginPage.InternalAfterXpath;
			String InternalPort = getDriver().findElement(By.xpath(actualXpath_InternalPort)).getText();
			System.out.println("InternalPort :" + InternalPort);

			String actualXpath_carrierName = TAILoginPage.BeforeXpath + row + TAILoginPage.CarrierNameXpath;
			String CarrierName = getDriver().findElement(By.xpath(actualXpath_carrierName)).getText();
			System.out.println("Carrier Name :" + CarrierName);
			
			if(row==3 && Portable.equals("N")) {
				break ;
			}
			if (Portable.equals("Y") && Wireless.equals("N") && InternalPort.equals("N")) {
				reader.setCellData(sheetName, "TelephoneNumber", storecount, TelePhoneNo);
				reader.setCellData(sheetName, "Portable", storecount, Portable);
				reader.setCellData(sheetName, "Wireless", storecount, Wireless);
				reader.setCellData(sheetName, "InternalPort", storecount, InternalPort);
				reader.setCellData(sheetName, "CarrierName", storecount, CarrierName);
			}else {
				storecount=storecount-1;
			}
			storecount++;
		}
		}
		totalTNS=reader.getRowCount(sheetName);
		totalTNS=totalTNS-1;
		counttotalTNS=totalTNS;
		System.out.println("Number of Portable TNs in the sheet is second time:: "+totalTNS);
		System.out.println("**************************************************");
	}
	@Step
	public void SendEmailhavingOrderDetails() {
		DateFormat dfobj = new SimpleDateFormat("MM/dd/yyyy");

		Date dateobj = new Date();

		System.out.println(dfobj.format(dateobj));

		System.out.println("Sending Order Details in email...");

		java.util.Properties props = new java.util.Properties();

		props.put("mail.smtp.host", "mailgate.uswc.uswest.com");

		Session session = Session.getDefaultInstance(props, null);

		String from = "nnanda.kumar@centurylink.com";

		String subject = "Bulk TNs data List_" + dfobj.format(dateobj);

		Message msg = new MimeMessage(session);

		try {

			msg.setFrom(new InternetAddress(from));

			//msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse("nnanda.kumar@CenturyLink.com, Praveen.K.Chinni@centurylink.com, Suman.Banka@centurylink.com, Dhilliswararao.Seepana@centurylink.com, heather.cox@centurylink.com, suma.pujari@centurylink.com, Keith.Lamle@CenturyLink.com "));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("nnanda.kumar@CenturyLink.com"));
			msg.setSubject(subject);
			// MimeMultipart multipart = new MimeMultipart("related");

			BodyPart msgBodyPart = new MimeBodyPart();

			// msgBodyPart.setContent(htmlText, "text/html");
			String test = "Please find the attachement for Portable Telephone number details... "+"\n"+"Refer the sheet with name ::" + " ' "+sheetName +" '"+", Number of Portable TNs in the sheet is["+totalTNS+"]";
			msgBodyPart.setText(test);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(msgBodyPart);

			// Part two is attachment
			msgBodyPart = new MimeBodyPart();
			String filename = System.getProperty("user.dir") + "//src//test//resources//TNData.xlsx";
			DataSource source = new FileDataSource(filename);
			msgBodyPart.setDataHandler(new DataHandler(source));
			msgBodyPart.setFileName("TN_DataList.xlsx");
			multipart.addBodyPart(msgBodyPart);
			msg.setContent(multipart);
			Transport.send(msg);
			System.out.println("Sent email successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}