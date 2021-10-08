package com.nicetravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Travel;
import com.nicetravel.service.TravelService;




@Controller
@RequestMapping("/travel")
public class TravelController {

	@Autowired
	TravelService travelService;
	
	@GetMapping
	public String list(Model model) {
//		List<Travel> travels = travelService.getAllTravel();
//		model.addAttribute("travels", travels);
		return "travel/list";
	}
	
	@RequestMapping("/travel/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Travel item = travelService.findById(id);
		model.addAttribute("item", item);
		return "travel/detail";
	} 
}
