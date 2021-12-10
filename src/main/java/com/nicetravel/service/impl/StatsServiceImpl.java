package com.nicetravel.service.impl;

import java.time.YearMonth;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicetravel.repository.StatsRepository;
import com.nicetravel.service.StatsService;
@Service
public class StatsServiceImpl implements StatsService{

	@Autowired
	private StatsRepository repo;
	
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


}
