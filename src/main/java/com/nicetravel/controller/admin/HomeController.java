package com.nicetravel.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.service.StatsService;

@Controller(value = "homeControllerOfAdmin")
@RequestMapping("/admin")
public class HomeController {
	@Autowired
	private StatsService statsService;
	@GetMapping("")
	public String doGetIndex(Model model) {
		String[][] chartData = statsService.getTotalPriceLast6Month();
		for(int i =0 ; i<6; i++) {
			System.out.println(chartData[0][i]);
			System.out.println(chartData[1][i]);
			System.out.println("");
		}
		model.addAttribute("chartData",chartData);
		return "admin/index";
	}
	
	@GetMapping("/danh-sach-tour")
	public String danhSachTour() {
		return "admin/DanhSachTour";
	}
	
	@GetMapping("/chinh-sua-tour")
	public String chinhSuaTour() {
		return "admin/ChinhSuaTour";
	}
	
	@GetMapping("/thong-tin-khach-hang")
	public String thongTinKhachHang() {
		return "admin/khach-hang/ThongTinKhachHang";
	}
	
	@GetMapping("/thong-tin-nhan-vien")
	public String thongTinNhanVien() {
		return "admin/nhan-vien/ThongTinNhanVien";
	}
}
