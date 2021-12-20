package com.nicetravel.service.impl;

import java.time.YearMonth;
import java.util.Iterator;

import com.nicetravel.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicetravel.repository.StatsRepository;
import com.nicetravel.service.StatsService;
@Service
public class StatsServiceImpl implements StatsService{

	@Autowired
	private StatsRepository repo;

	@Autowired
	private TravelRepository travel;
	
	@Override
	public String[][] getTotalPriceLast6Month() {
		String[][] result = new String [2][6];
		
		YearMonth now = YearMonth.now();
		for(int i =0 ; i<6; i++) {
			String month = now.minusMonths((long)i).getMonthValue()+"";
			String year = now.minusMonths((long)i).getYear()+"";
			result[0][5-i] = month +"-"+year;
			result[1][5-i] = repo.getTotalPriceOneMonth(month, year);
			
		}
		return result;
	}

	@Override
	public String[][] sp_getTotalTravelOneMonth() {
		String[][] result = new String [2][12];

		YearMonth now = YearMonth.now();
		for(int i = 0 ; i < 12; i++) {
			String month = i +1+"";

			String year = now.getYear() + "";
			result[0][i] = month + "-" + year;
			result[1][i] = repo.sp_getTotalTravelOneMonth(month, year);
		}
		return result;
	}

	@Override
	public String[][] sp_getTotalBookingOneMonth() {
		String[][] result = new String [2][12];

		YearMonth now = YearMonth.now();
		for(int i = 0 ; i < 12; i++) {
			String month = i +1+"";

			String year = now.getYear() + "";
			result[0][i] = month + "-" + year;
			result[1][i] = repo.sp_getTotalBookingOneMonth(month, year);
		}
		return result;
	}

	@Override
	public String[][] sp_GetTotalTravelLike() {
		String[][] result1 = repo.sp_GetTotalTravelLike();
		System.out.println(result1.length);
		String[][] result = new String[2][result1.length];
		for(int i =0 ; i<result1.length; i++) {
			result[0][result1.length- 1 - i] = result1[result1.length- 1 - i][0];
			result[1][result1.length- 1 - i] = result1[result1.length- 1 - i][1];
		}
		return result;
	}


}
