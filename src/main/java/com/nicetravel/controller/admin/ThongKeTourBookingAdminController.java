package com.nicetravel.controller.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.BookingDetail;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingDetailService;
import com.nicetravel.service.BookingService;
import com.nicetravel.service.TravelService;

@Controller
@RequestMapping("/admin")

public class ThongKeTourBookingAdminController {
	private static final int SIZE = 4;
	private final AccountService accountService;
	@Autowired
	public ThongKeTourBookingAdminController(AccountService accountService) {
		this.accountService = accountService;
	}
	@Autowired
	TravelService travelService;
	
	@Autowired
	BookingDetailService bookingDetailService;
	
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
		
		
		return "admin/thong-ke/ThongKe-Booking";
	}
}
