package com.nicetravel.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelLike;
import com.nicetravel.security.auth.CustomOAuth2User;
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
	
	@Autowired
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
		
		travelService.updateEX();
		
		return "travel/list2";
	}

	@RequestMapping("/detail/{slug}")
	public String detail(Model model,@PathVariable("slug") String slug) {

		Travel item = travelService.findTravelBySlug(slug);
		List<Travel> listFavo = travelService.getFavoriteTour();
		model.addAttribute("favoriteItems", listFavo);
		model.addAttribute("item", item);

		return "travel/detail2";
	}

	@RequestMapping("/tour")
	public String tour(Model model, @RequestParam("tid") Optional<Integer> tid,@RequestParam(name="p", defaultValue ="1") int p) {
		if (tid.isPresent()) {
		//	List<Travel> list = travelService.findByTypeId(tid.get());
			Pageable pageable = PageRequest.of(p-1, 9);
			Page<Travel> list = travelService.findByTypeId(tid.get(),pageable);
			
			model.addAttribute("currentURL", request.getQueryString().toString());
			model.addAttribute("items", list);
			int n =2;
			model.addAttribute("n", n);
		} else {
			Pageable pageable = PageRequest.of(p-1, 9);
			Page<Travel> list = travelService.getAll(pageable);
			model.addAttribute("items", list);
		}
		return "travel/tour2";
	}
	@RequestMapping("/like/{id}")
	public String like(@PathVariable("id") Integer id, Authentication authentication, HttpServletRequest request) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser()); // remote

		String username = null;

		if (account == null){
			CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
			Account accountOauth = accountService.findByEmail(oauth2User.getEmail());
			username = accountOauth.getUsername();
		}
		else {
			username = account.getUsername();
		}
		account = accountService.findAccountsByUsername(username);
		Travel travel = travelService.findTravelById(id);
		
		TravelLike like = new TravelLike();
		like.setTravel_like_account_id(account);
		like.setTravelId(travel);
		travelLikeService.createTravelLike(like);

		return "forward:/travel/tour";
	}
}
