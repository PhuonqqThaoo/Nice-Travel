package com.nicetravel.controller.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

import com.nicetravel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.BookingDetail;

@Controller
@RequestMapping("/admin")

public class ThongKeTourBookingAdminController {
	private static final int SIZE = 8;
	private final AccountService accountService;
	@Autowired
	public ThongKeTourBookingAdminController(AccountService accountService) {
		this.accountService = accountService;
	}
	@Autowired
	TravelService travelService;
	
	@Autowired
	BookingDetailService bookingDetailService;

	@Autowired
	StatsService statsService;

	@RequestMapping("/thongke-tourbooking")
	public String getThongKeTourBooking(HttpServletRequest request, Model model,String start, String end,
			 @RequestParam(name="page",defaultValue = "1") int page) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		
		Integer tourInMonth = travelService.countTourInMonth();
		model.addAttribute("tourInMonth", tourInMonth);

		Integer tourDaDat = travelService.sp_GetTourDaDat();
		model.addAttribute("tourDaDat", tourDaDat);
		
		Page<BookingDetail> Infor = bookingDetailService.getAllBookingDetail(start, end, page-1,SIZE);
		model.addAttribute("inforTour", Infor);
		
		
		model.addAttribute("totalPage", Infor.getTotalPages());
		model.addAttribute("currentPage", page);

		String[][] chartData;
		chartData = statsService.sp_getTotalBookingOneMonth();
		for(int i = 0 ; i<12; i++) {
			System.out.println(chartData[0][i]);
			System.out.println(chartData[1][i]);
			System.out.println("");
		}
		String text = "Thống kê số lượng tour đã đặt trong 12 tháng";
		model.addAttribute("text",text);
		System.out.println(chartData);
		model.addAttribute("chartData",chartData);

		return "admin/thong-ke/ThongKe-Booking";
	}
}
