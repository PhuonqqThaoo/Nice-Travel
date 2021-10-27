package com.nicetravel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelDetail;
import com.nicetravel.service.TravelDetailService;
import com.nicetravel.service.TravelService;


@Controller
@RequestMapping("/travel")
public class TravelController {

	@Autowired
	TravelService travelService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		
		
		 List<Travel> list = travelService.getAllTravel();
		 model.addAttribute("items",list);
		 
		return "travel/list";
	}
	 
	@RequestMapping("/detail/{id}")
	public String detail(Model model,@PathVariable("id") Integer id) {

		  Travel item = travelService.findTravelById(id);
		  System.out.println(id);
		  List<Travel> list = travelService.getAllTravel();
		  model.addAttribute("items",list);
	  
		  model.addAttribute("item", item);
		  
		 
		return "travel/detail";
	} 
	@RequestMapping("/tour")
	public String tour(Model model) {
		List<Travel> list = travelService.getAllTravel();
		model.addAttribute("items", list);
		return "travel/tour";
	}

}
