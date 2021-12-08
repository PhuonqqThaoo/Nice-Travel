package com.nicetravel.controller.rest;

import com.nicetravel.entity.Total;
import com.nicetravel.repository.AccountRepository;
import com.nicetravel.repository.BookingRepository;
import com.nicetravel.repository.StatsRepository;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingService;
import com.nicetravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/staff")
public class HomeStaffRestController {

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

    @Autowired
    private StatsRepository statsService;

    private static final int SIZE =4;

    @GetMapping("/count/bookingInDay")
    public Integer getBookingInDay() {
        return bookingService.getBookingInDay();
    }

    // doanh thu trong ngày
    @GetMapping("/total/RevenueInDay")
    public Double getRevenueInDay() {
        return bookingService.getRevenueInDay();
    }

    // tổng doanh thu
    @GetMapping("/total/Revenue")
    public Double getRevenue() {
        return bookingService.getRevenue();
    }
    // doanh thu so với năm trước
    @GetMapping("/sinceYesterday")
    public Double getRevenueSinceYesterday() {
        double currentMonth = bookingRepository.getRevenue();
        double lastMonth = bookingRepository.getLastRevenue();
        double result1 = ((currentMonth / lastMonth) * 100) -100;
        System.out.println(result1);
        return bookingService.getComparedLastYear();

    }

    // tổng khách hàng
    @GetMapping("/total/user")
    public Integer getTotalUser() {
        return accountService.getTotalUsers();
    }

    // khách hàng so với tháng trước
    @GetMapping("/totalUserLastMonth")
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

    @GetMapping("/TotalSold")
    public List<Total> getTotalSold() {
        return travelService.getTotal();

    }

//	@GetMapping("/stats")
//	public String getTotalPriceOneMonth(){
//		return statsService.getTotalPriceOneMonth();
//	}


}

