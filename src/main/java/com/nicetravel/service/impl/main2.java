package com.nicetravel.service.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class main2 {
	public static void main(String[] args) {
		Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy");
        String year = simpleDateFormat.format(date);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat();
        simpleDateFormat1.applyPattern("MM");
		String day = date.getDay()+"";
		String month =simpleDateFormat1.format(date);
		System.out.println(day);
		System.out.println(month);
		System.out.println(year);

		
//		 Date date = new Date();
//		 
//	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
//	        simpleDateFormat.applyPattern("yyyy");
//	        String year = simpleDateFormat.format(date);
//	        
//	        System.out.println(year);
		
		
		
	}
}
