package com.nicetravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Travel;
import com.nicetravel.service.TravelService;

@Controller
@RequestMapping("booking")
public class BookingController {
	@Autowired
	TravelService travelService;
	
	@RequestMapping("check")
	public String check(Model model) {
		//danh sách tour
		List<Travel> list = travelService.getAllTravel();
		  model.addAttribute("items",list);
		return "booking/list";
	}
	
}
