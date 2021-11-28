package com.nicetravel.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.ListTravelLike;
import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelLike;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.BookingService;
import com.nicetravel.service.StatsService;
import com.nicetravel.service.TravelLikeService;
import com.nicetravel.service.TravelService;

@Controller(value = "homeControllerOfAdmin")
public class HomeController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private StatsService statsService;
	
	@Autowired
	private AccountService acc;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private TravelService travel;
	
	@Autowired
	private BookingService booking;
	
	@Autowired
	private TravelLikeService travelLike;
	
	@RequestMapping("/admin")
	public String doGetIndex(Model model,HttpServletRequest request) throws Exception {
		List<Account> list = accountService.findAll();
		model.addAttribute("listUser",list);
		List<Travel> listFavo = travel.getFavoriteTour();
		model.addAttribute("favoriteItems", listFavo);
		System.out.println(listFavo);
		String username = request.getRemoteUser();
		System.out.println(username);
	    String day1 = request.getParameter("day");
	    String end1 = request.getParameter("end");
	    model.addAttribute("day",day1);
	    model.addAttribute("end",end1);
		String[][] chartData;
		String[][] chartData1;
		if(ObjectUtils.isEmpty(day1) && ObjectUtils.isEmpty(end1)) {
			chartData = statsService.getTotalPriceLast6Month();
			for(int i =0 ; i<6; i++) {
				System.out.println(chartData[0][i]);
				System.out.println(chartData[1][i]);
				System.out.println("");
			}
			String text = "Thống kê doanh thu 6 tháng";
			model.addAttribute("text",text);
			System.out.println(chartData);
			model.addAttribute("chartData",chartData);
		}
		else {
			chartData = booking.getTotalPriceFromTo(day1, end1);
			System.out.println(chartData);
			String text = "Thống kê doanh thu từ ngày " + day1+" đến ngày "+end1 ;
			model.addAttribute("text",text);
			model.addAttribute("chartData",chartData);
			
		}
		return "admin/index";
	}
	

//	@GetMapping("/admin/quan-ly/tour-du-lich")
//	public String quanLyTour() {
//		return "admin/quan-ly/tour-du-lich/QuanLy-TourDuLich";
//	}
	
//	@GetMapping("/thong-tin-khach-hang")
//	public String thongTinKhachHang() {
//		return "admin/khach-hang/ThongTinKhachHang";
//	}
	
//	@GetMapping("/thong-tin-nhan-vien")
//	public String thongTinNhanVien() {
//		return "admin/nhan-vien/ThongTinNhanVien";
//	}
	
	
}
