package com.nicetravel.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/thong-ke")
public class StatisticalController {
	@GetMapping("/tour-du-lich")
	public String getThongKeTour() {
		return "admin/thong-ke/tour-du-lich/ThongKe-TourDuLich";
	}
}
