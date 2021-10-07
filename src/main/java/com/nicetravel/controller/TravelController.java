package com.nicetravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Travel;
import com.nicetravel.service.TravelService;

@Controller
public class TravelController {

	@Autowired
	TravelService travelService;
	
	@RequestMapping("/travel/list")
	public String list(Model model) {
		/*
		 * List<Travel> list = travelService.getAllTravel(); model.addAttribute("items",
		 * list);
		 */
		return "travel/list";
	}
}