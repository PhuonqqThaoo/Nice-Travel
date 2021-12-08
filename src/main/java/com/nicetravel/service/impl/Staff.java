package com.nicetravel.service.impl;

import java.time.YearMonth;

public class Staff {
	public static void main(String[] args) {
		String[][] result = new String [2][12];

		YearMonth now = YearMonth.now();
		for(int i = 0 ; i < 12; i++) {
			String month = i +1+"";
			String year = now.getYear() + "";
			result[0][i] = month + "-" + year;
			result[1][i] = "1";
			System.out.println(result[0][i]);
			System.out.println(result[1][i]);
		}
		
	}
	
}
