package com.nicetravel.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicetravel.service.BookingService;
import com.nicetravel.service.TravelService;

@CrossOrigin("*")
@RestController
public class HomeAdminRestController {
	@Autowired
	private TravelService travelService;
	
	@Autowired
	private BookingService bookingService;
	
	@GetMapping("/admin/count/travel")
	public Integer getCountTravel() {
	
		return travelService.getCountTravel();
	}
	
	
	@GetMapping("/admin/count/bookingInDay")
	public Integer getBookingInDay() {
		return bookingService.getBookingInDay();
	}
	
	@GetMapping("/admin/total/RevenueInDay")
	public Double getRevenueInDay() {
		return bookingService.getRevenueInDay();
	}
	
	
}
