package com.ctl.it.qa.yourapplication.tools.pages.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {

	public static void main(String[] args) {
		Random generator = new Random();
		// generate a random integer from 0 to 899, then add 100
		//for(int i=0;i<=1000;i++) {
		//int randomNPA=generator.nextInt(900) + 100;
		//System.out.println(randomNPA);
		
		Xls_Reader reader = new Xls_Reader(System.getProperty("user.dir") + "//src//test//resources//TNData.xlsx");

		System.out.println(reader.getRowCount("TNDataList48")-1);
		int s=0;
		List<Integer> lis=new ArrayList<>();
		for (int row = 1; row <= 10; row++) { 
			//should be 11 or 1001
			lis.add(row);
			System.out.println(s++);
		}
		
	}
}
