package com.nicetravel.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelLike;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.TravelLikeService;
import com.nicetravel.service.TravelService;

@Controller
@RequestMapping("/travel")
public class TravelController {

	@Autowired
	TravelService travelService;
	@Autowired

	HttpServletRequest request;

	AccountService 	accountService;
	@Autowired
	TravelLikeService travelLikeService;

	@RequestMapping("/list")
	public String list(Model model) {
		List<Travel> list = travelService.getAllTravel();
		model.addAttribute("items", list);
		
		List<Travel> listFavo = travelService.getFavoriteTour();
		model.addAttribute("favoriteItems", listFavo);
		
		Integer tourDaLat = travelService.countDaLatTour();
		model.addAttribute("countDaLat", tourDaLat +" Tours");
		
		Integer tourHaNoi = travelService.countHaNoiTour();
		model.addAttribute("countHaNoi", tourHaNoi +" Tours");

		Integer tourDaNang = travelService.countDaNangTour();
		model.addAttribute("countDaNang", tourDaNang +" Tours");
		
		Integer tourPhuQuoc = travelService.countPhuQuocTour();
		model.addAttribute("countPhuQuoc", tourPhuQuoc +" Tours");
		
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
	public String tour(Model model, @RequestParam("tid") Optional<Integer> tid,@RequestParam(name="p", defaultValue ="1") int p) {
		if (tid.isPresent()) {
		//	List<Travel> list = travelService.findByTypeId(tid.get());
			Pageable pageable = PageRequest.of(p-1, 6);
			Page<Travel> list = travelService.findByTypeId(tid.get(),pageable);
			model.addAttribute("currentURL", request.getQueryString().toString());
			model.addAttribute("items", list);
			int n =2;
			model.addAttribute("n", n);
		} else {
			Pageable pageable = PageRequest.of(p-1, 6);
			Page<Travel> list = travelService.getAll(pageable);
			model.addAttribute("items", list);
		}
		return "travel/tour";
	}
	@RequestMapping("/like/{id}")
	public String like(@PathVariable("id") Integer id, HttpServletRequest request) {
		String username = request.getRemoteUser();
		Account account = accountService.findAccountsByUsername(username);
		Travel travel = travelService.findTravelById(id);
		
		TravelLike like = new TravelLike();
//		like.setAccountIdFk(account);
		like.setTravelId(travel);
		travelLikeService.createTravelLike(like);
		
		return "redirect:/";
	}
}
