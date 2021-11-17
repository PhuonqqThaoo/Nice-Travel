package com.nicetravel.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.Travel;
import com.nicetravel.service.TravelService;

@Controller
@RequestMapping("/travel")
public class TravelController {

	@Autowired
	TravelService travelService;

	@RequestMapping("/list")
	public String list(Model model) {

		List<Travel> list = travelService.getAllTravel();
		model.addAttribute("items", list);
		return "travel/list";
	}

	@RequestMapping("/detail/{slug}")
	public String detail(Model model,@PathVariable("slug") String slug) {

		Travel item = travelService.findTravelBySlug(slug);
		List<Travel> list = travelService.getAllTravel();
		model.addAttribute("items", list);

		model.addAttribute("item", item);

		return "travel/detail";
	}

	@RequestMapping("/tour")
	public String tour(Model model, @RequestParam("tid") Optional<Integer> tid) {
		if (tid.isPresent()) {
			List<Travel> list = travelService.findByTypeId(tid.get());
			model.addAttribute("items", list);
		} else {
			List<Travel> list = travelService.getAllTravel();
			model.addAttribute("items", list);
		}
		return "travel/tour";
	}

}
