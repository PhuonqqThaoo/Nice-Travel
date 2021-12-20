package com.nicetravel.service;

public interface StatsService {
	String[][] getTotalPriceLast6Month();
	
	String[][] sp_getTotalTravelOneMonth();

	String[][] sp_getTotalBookingOneMonth();

	String[][]  sp_GetTotalTravelLike();
}