package com.nicetravel.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.Travel;
import com.nicetravel.service.TravelService;

@Controller
@RequestMapping("/tour")
public class TourController {


	@Autowired
	TravelService travelService;
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/search/p={p}")
	public String search(Model model,

			@PathVariable("p") Integer p,
			@RequestParam ("departurePlace") String depart,
			@RequestParam("destinationPlace") String desti,
			@RequestParam("startDate") String sd,
			@RequestParam("price-min") BigDecimal pmin,
			@RequestParam("price-max") BigDecimal pmax) {
			
			Pageable pageable = PageRequest.of(p-1, 9);

			Page<Travel> list = travelService.searchTour2(depart, desti, sd, pmin, pmax,pageable);
			model.addAttribute("items", list);
			model.addAttribute("departurePlace", depart);
			model.addAttribute("destinationPlace", desti);
			model.addAttribute("startDate", sd);
			model.addAttribute("price-min", pmin);
			model.addAttribute("price-max", pmax);
			

			model.addAttribute("currentURL", request.getQueryString().toString());
		return "travel/search2";

			
	}
	
	
}
