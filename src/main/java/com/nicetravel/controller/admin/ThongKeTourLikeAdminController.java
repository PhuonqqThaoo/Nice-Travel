package com.nicetravel.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nicetravel.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.BookingDetail;
import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelLike;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.TravelLikeService;
import com.nicetravel.service.TravelService;

@Controller
@RequestMapping("/admin")
public class ThongKeTourLikeAdminController {
	private static final int SIZE = 3;
	private final AccountService accountService;
	@Autowired
	TravelLikeService travelLikeService;

	@Autowired
	StatsService statsService;

	@Autowired
	public ThongKeTourLikeAdminController(AccountService accountService) {
		this.accountService = accountService;
	}
	@Autowired
	TravelService travel;
	
	@RequestMapping("/thongke-tourlike")
	public String getThongKeTourBooking(HttpServletRequest request, Model model, @RequestParam(name="page",defaultValue = "1") int page) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		
		Page<TravelLike> favoriteTour = travelLikeService.getAll(page -1, SIZE);
		//Page<TravelLike> favoriteTour = travelLikeService.getFavotiteTour(page -1, SIZE);
		model.addAttribute("favoriteTour", favoriteTour);
		
		//List<Travel> listFavo = travel.getFavoriteTour();
		//model.addAttribute("favoriteItems", listFavo);
		
		model.addAttribute("totalTour", travelLikeService.countTourLike());
		model.addAttribute("totalPage", favoriteTour.getTotalPages());
		model.addAttribute("currentPage", page);

		String[][] chartData;
		chartData = statsService.sp_GetTotalTravelLike();
		String text = "Thống kê số lượng yêu thích của các tour";
		model.addAttribute("text",text);
		System.out.println(chartData);
		model.addAttribute("chartData",chartData);

		return "admin/thong-ke/ThongKe-LikeTravel";
	}
}
