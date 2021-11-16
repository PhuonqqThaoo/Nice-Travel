package com.nicetravel.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.StatsService;
import com.nicetravel.service.TravelService;

@Controller(value = "homeControllerOfAdmin")
@RequestMapping("/admin")
public class HomeController {
	@Autowired
	private StatsService statsService;
	@Autowired
	private AccountService acc;
	
	@Autowired
	private TravelService travel;
	
	@GetMapping("")
	public String doGetIndex(Model model) {
		String[][] chartData = statsService.getTotalPriceLast6Month();
		
//		for(int i =0 ; i<6; i++) {
//			System.out.println(chartData[0][i]);
//			System.out.println(chartData[1][i]);
//			System.out.println("");
//		}
//		System.out.println(list);
		model.addAttribute("chartData",chartData);
		return "admin/index";
	}
	
	@GetMapping("/quan-ly/tour-du-lich")
	public String quanLyTour() {
		return "admin/quan-ly/tour-du-lich/QuanLy-TourDuLich";
	}
	
//	@GetMapping("/thong-tin-khach-hang")
//	public String thongTinKhachHang() {
//		return "admin/khach-hang/ThongTinKhachHang";
//	}
	
//	@GetMapping("/thong-tin-nhan-vien")
//	public String thongTinNhanVien() {
//		return "admin/nhan-vien/ThongTinNhanVien";
//	}
	
	
}
