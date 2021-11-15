package com.nicetravel.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicetravel.entity.Total;
import com.nicetravel.entity.Travel;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.repository.BookingRepository;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingService;

import com.nicetravel.service.TravelService;

@CrossOrigin("*")
@RestController
public class HomeAdminRestController {
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private TravelService travelService;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountRepository accountRepo;
	
	@GetMapping("/admin/count/travel")
	public Integer getCountTravel() {
	
		return travelService.getCountTravel();
	}
	
	
	@GetMapping("/admin/count/bookingInDay")
	public Integer getBookingInDay() {
		return bookingService.getBookingInDay();
	}
	
	// doanh thu trong ngày
	@GetMapping("/admin/total/RevenueInDay")
	public Double getRevenueInDay() {
		return bookingService.getRevenueInDay();
	}
	
	// tổng doanh thu
	@GetMapping("/admin/total/Revenue")
	public Double getRevenue() {
		return bookingService.getRevenue();
	}
	 // doanh thu so với năm trước
	@GetMapping("/admin/sinceYesterday")
	public Double getRevenueSinceYesterday() {
		double currentMonth = bookingRepository.getRevenue();
		double lastMonth = bookingRepository.getLastRevenue();
		double result1 = ((currentMonth / lastMonth) * 100) -100;
		System.out.println(result1);
		return bookingService.getComparedLastYear();
		
	}
	
	// tổng khách hàng
	@GetMapping("/admin/total/user")
	public Integer getTotalUser() {
		return accountService.getTotalUsers();
	}
	
	// khách hàng so với tháng trước
	@GetMapping("/admin/totalUserLastMonth")
	public Double gettotalUserLastMonth() {
		double currentMonth = accountRepo.getTotalUsers();
		double lastMonth = accountRepo.getTotalUserLastMonth();
		double result = ((currentMonth / lastMonth) * 100) -100;
		System.out.println("đât là kqua"+currentMonth);
		System.out.println("đât là kqua"+lastMonth);
		System.out.println("đât là kqua"+result);
		return accountService.comparedLastMonth();
		
	}
	
	
	
//	// số lượng đã đặt so với số lượng ban đầu
//		@GetMapping("/admin/TotalSold")
//		public List<String[][]> getTotalSold() {
//			return travelService.getTotalSold();
//		}
	
	@GetMapping("/admin/TotalSold")
	public List<Total> getTotalSold() {
		return travelService.getTotal();
	}
}
