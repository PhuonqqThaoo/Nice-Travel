package com.nicetravel.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.Travel;
import com.nicetravel.service.TravelService;

@Controller
@RequestMapping("/tour")
public class TourController {


	@Autowired
	TravelService travelService;
	
	@RequestMapping("/search")
	public String search(Model model,@RequestParam ("departurePlace") Optional<String> depart,
			@RequestParam("destinationPlace") Optional<String> desti,
			@RequestParam("startDate") Optional<String> sd,
			@RequestParam("price-min") Optional<BigDecimal> pmin,
			@RequestParam("price-max") Optional<BigDecimal> pmax) {
		
			List<Travel> list = travelService.searchTour(depart.get(), desti.get(), sd.get(), pmin.get(), pmax.get());
			model.addAttribute("items", list);
		
		return "travel/tour";
	}
}
