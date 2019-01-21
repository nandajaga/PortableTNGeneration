package com.ctl.it.qa.yourapplication.tools.steps.user;

import net.thucydides.core.annotations.Step;

import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.ctl.it.qa.staf.xml.reader.IntDataContainer;
import com.ctl.it.qa.yourapplication.tools.pages.common.yourApplicationHomePage;
import com.ctl.it.qa.yourapplication.tools.pages.common.yourApplicationLoginPage;
import com.ctl.it.qa.yourapplication.tools.pages.common.NumsPage;
import com.ctl.it.qa.yourapplication.tools.steps.YourApplicationSteps;
import com.ctl.it.qa.yourapplication.tools.pages.common.TAILoginPage;
import com.ctl.it.qa.yourapplication.tools.pages.common.Xls_Reader;

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
	List<String> blockbeginrange = new ArrayList<>();
	List<String> blockendrange = new ArrayList<>();
	String NPA;
	String NXX;
	String TN;
	String beginRange;
	String EndRange;
	String Number_of_Entries;
	List<Integer> loop1=new ArrayList<>();
	List<String> TNsList=new ArrayList<>();
	List<String> WirelessList=new ArrayList<>();
	List<String> InternalPortList=new ArrayList<>();	
	List<String> CarrierNameList=new ArrayList<>();	
	List<Long> originalTNs;
	List<String> AssignedTNSentry=new ArrayList<>();
	List<String> TelePhoneNoList=new ArrayList<>();
	Xls_Reader reader = new Xls_Reader(System.getProperty("user.dir") + "//src//test//resources//TNData.xlsx");

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
		//String NPA = reader.getCellData("NPANXX", "NPA", 2);
		Random generator = new Random();
		// generate a random integer from 0 to 899, then add 100
		int randomNPA=generator.nextInt(900) + 100;
		NPA=Integer.toString(randomNPA);
		//NPA="561";
		System.out.println("NPA value :"+NPA);
		NumsPage.tbx_NPA.clear();
		NumsPage.tbx_NPA.sendKeys(NPA);
		//NumsPage.tbx_NPA.sendKeys("123");
		
		System.out.println("Enter NXX value :");
		int randomNXX=generator.nextInt(900) + 100;
		//NXX="802";
		NXX=Integer.toString(randomNXX);
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
			//System.exit(0);
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

		for (int i = 1; i <= row.size(); i++) {

			beginRange = getDriver().findElement(By.xpath(before_xpath + i + after_xpath)).getText();
			System.out.println(i + "_" + "beginRange: " + beginRange);

			 EndRange = getDriver().findElement(By.xpath(before_xpath + i + after_xpath1)).getText();
			System.out.println(i + "_" + "endRange: " + EndRange);
			loop = Integer.parseInt(EndRange) - Integer.parseInt(beginRange);		
			loop1.add(loop);
			
			System.out.println("TOtal Telephone numbers will be :" +loop);
			String startRangePH = NPA + NXX + beginRange;
			String EndRangePH = NPA + NXX + EndRange;

			System.out.println(i + "_" + "StartRange :" + startRangePH);
			System.out.println(i + "_" + "EndRange :" + EndRangePH);
			blockbeginrange.add(startRangePH);
			blockendrange.add(EndRangePH);
			
		}
		NUMSWindow = getDriver().getWindowHandle();
		searchAssignedTNs(); 
		ThousandTNS();
	}
public void ThousandTNS() throws IOException, InterruptedException{
	int count=0;
	closeloop:
	while(count<=blockbeginrange.size()-1){
		if(loop1.get(count)==999) {
			if(AssignedTNSentry.get(count).equals("0 entries found")) {
				TN=blockbeginrange.get(count);
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
		checkAssignedTNs();
		setTNValues(); //calling this in checkAssignedTNs()
	}

	public void searchAssignedTNs() {
	
	int count=0;
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
	public void checkAssignedTNs() throws IOException, InterruptedException {
		/*originalTNs= new ArrayList<>();
		long s = Long.parseLong(TN);
		for (int row = 1; row <= loop+1; row++) {
			originalTNs.add(s++);
		}*/
		
		/*int count=0;
		while(count<=AssignedTNSentry.size()-1) {
			if(AssignedTNSentry.get(count).equals("0 entries found")) {
				TN=blockbeginrange.get(count);
				System.out.println(":::::::::::::"+TN+"::::::::::::::::");
				break;
				//setTNValues(); // for all the listed numbers, for each range a email will be sent
			}
		count++;
		}if(count>=AssignedTNSentry.size()) {
			getDriver().switchTo().defaultContent();
			NPA_NXX_values_and_store_the_values_in_sheet();
		}*/
		
	}
	public void setTNValues() {
		originalTNs = new ArrayList<>();
		long s = Long.parseLong(TN);
		for (int row = 1; row <= loop + 1; row++) {
			originalTNs.add(s++);
		}
		getDriver().switchTo().window(TaiWindow);
		TAILoginPage.tbx_TN.clear();
		for (Long s1 : originalTNs) {
			TAILoginPage.tbx_TN.sendKeys(s1 + "\n");
		}

		TAILoginPage.btn_Execute.click();
		List<WebElement> Tairows1 = getDriver().findElements(By.xpath("//*[@id='mainform']//table[3]//tr"));
		int Tairows = Tairows1.size();
		System.out.println("Total Tairows =>" + Tairows);

		Random generator = new Random();
		int randNo = generator.nextInt(100);
		sheetName = "TNDataList" + Integer.toString(randNo);
		System.out.println("##################" + sheetName + "##################");
		//reader.removeSheet(sheetName);
		if (!reader.isSheetExist(sheetName)) {
			reader.addSheet(sheetName);
			reader.addColumn(sheetName, "TelephoneNumber");
			reader.addColumn(sheetName, "Portable");
			reader.addColumn(sheetName, "Wireless");
			reader.addColumn(sheetName, "InternalPort");
			reader.addColumn(sheetName, "CarrierName");
		}
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

			if (Portable.equals("Y") && Wireless.equals("N") && InternalPort.equals("N")) {
				reader.setCellData(sheetName, "TelephoneNumber", row, TelePhoneNo);
				reader.setCellData(sheetName, "Portable", row, Portable);
				reader.setCellData(sheetName, "Wireless", row, Wireless);
				reader.setCellData(sheetName, "InternalPort", row, InternalPort);
				reader.setCellData(sheetName, "CarrierName", row, CarrierName);
			}
		}
		
		System.out.println("**************************************************");
		System.out.println("Please refer the sheet for TN details with name : " + sheetName);
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

			msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse("nnanda.kumar@CenturyLink.com, Praveen.K.Chinni@centurylink.com, Suman.Banka@centurylink.com, Dhilliswararao.Seepana@centurylink.com"));
			//msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("nnanda.kumar@CenturyLink.com"));
			msg.setSubject(subject);
			// MimeMultipart multipart = new MimeMultipart("related");

			BodyPart msgBodyPart = new MimeBodyPart();

			// msgBodyPart.setContent(htmlText, "text/html");
			String test = "Please find the attachement for Portable Telephone number details... "+"\n"+"Refer the sheet with name :" + sheetName;
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