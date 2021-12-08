package com.nicetravel.controller.staff;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Travel;
import com.nicetravel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class HomeStaffContrller {


	private final AccountService accountService;

	private final StatsService statsService;

	private final HttpServletRequest request;

	private final TravelService travel;

	private final BookingService booking;

	@Autowired
	private TravelLikeService travelLike;

	private static final int SIZE = 4;

	@Autowired
	public HomeStaffContrller(AccountService accountService, StatsService statsService, HttpServletRequest request, TravelService travel, BookingService booking, TravelLikeService travelLike) {
		this.accountService = accountService;
		this.statsService = statsService;
		this.request = request;
		this.travel = travel;
		this.booking = booking;
		this.travelLike = travelLike;
	}

	@GetMapping("")
	public String doGetIndex(Model model,HttpServletRequest request,
							 @RequestParam(name="page",defaultValue = "1") int page)  throws Exception {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		List<Account> list = accountService.findAll();
		model.addAttribute("listUser",list);
		Page<Account> listByUser = accountService.findAllByUserActivate(page-1, SIZE);
		String title = "Danh sách khách hàng";
		model.addAttribute("title",title);
		model.addAttribute("listUser1", listByUser.getContent());
		model.addAttribute("totalPage", listByUser.getTotalPages());
		model.addAttribute("currentPage", page);
		List<Travel> listFavo = travel.getFavoriteTour();
		model.addAttribute("favoriteItems", listFavo);
		System.out.println(listFavo);
		String username = request.getRemoteUser();
		System.out.println(username);
		String day1 = request.getParameter("day");
		String end1 = request.getParameter("end");
		model.addAttribute("day",day1);
		model.addAttribute("end",end1);
		String[][] chartData;
		String[][] chartData1;
		if(ObjectUtils.isEmpty(day1) && ObjectUtils.isEmpty(end1)) {
			chartData = statsService.sp_getTotalTravelOneMonth();
			for(int i = 0 ; i<12; i++) {
				System.out.println(chartData[0][i]);
				System.out.println(chartData[1][i]);
				System.out.println("");
			}
			String text = "Thống kê tour 12 tháng";
			model.addAttribute("text",text);
			System.out.println(chartData);
			model.addAttribute("chartData",chartData);
		}
		else {
			chartData = booking.getTotalPriceFromTo(day1, end1);
			System.out.println(chartData);
			String text = "Thống kê doanh thu từ ngày " + day1+" đến ngày "+end1 ;
			model.addAttribute("text",text);
			model.addAttribute("chartData",chartData);

		}
		return "staff/index";
	}

	@GetMapping("/GetDate")
	public String doGetFindAllByUserActivateInGetDate(Model model,HttpServletRequest request,
													  @RequestParam(name="page",defaultValue = "1") int page)  throws Exception {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		List<Account> list = accountService.findAll();
		model.addAttribute("listUser",list);
		Page<Account> listByUser = accountService.findAllByUserActivateInGetDate(page-1, SIZE);
		String title = "Danh sách khách hàng mới đăng ký ngày hôm nay";
		model.addAttribute("title",title);
		model.addAttribute("listUser1", listByUser.getContent());
		model.addAttribute("totalPage", listByUser.getTotalPages());
		model.addAttribute("currentPage", page);
		List<Travel> listFavo = travel.getFavoriteTour();
		model.addAttribute("favoriteItems", listFavo);
		System.out.println(listFavo);
		String username = request.getRemoteUser();
		System.out.println(username);
		String day1 = request.getParameter("day");
		String end1 = request.getParameter("end");
		model.addAttribute("day",day1);
		model.addAttribute("end",end1);
		String[][] chartData;
		String[][] chartData1;
		if(ObjectUtils.isEmpty(day1) && ObjectUtils.isEmpty(end1)) {
			chartData = statsService.sp_getTotalTravelOneMonth();
			for(int i = 0 ; i<12; i++) {
				System.out.println(chartData[0][i]);
				System.out.println(chartData[1][i]);
				System.out.println("");
			}
			String text = "Thống kê tour 12 tháng";
			model.addAttribute("text",text);
			System.out.println(chartData);
			model.addAttribute("chartData",chartData);
		}
		else {
			chartData = booking.getTotalPriceFromTo(day1, end1);
			System.out.println(chartData);
			String text = "Thống kê doanh thu từ ngày " + day1+" đến ngày "+end1 ;
			model.addAttribute("text",text);
			model.addAttribute("chartData",chartData);

		}
		return "staff/index";
	}

	@GetMapping("/InMonth")
	public String doGetFindAllByUserActivateInMonth(Model model,HttpServletRequest request,
													@RequestParam(name="page",defaultValue = "1") int page)  throws Exception {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		List<Account> list = accountService.findAll();
		model.addAttribute("listUser",list);
		Page<Account> listByUser = accountService.findAllByUserActivateInMonth(page-1, SIZE);
		String title = "Danh sách khách hàng mới đăng ký trong tháng";
		model.addAttribute("title",title);
		model.addAttribute("listUser1", listByUser.getContent());
		model.addAttribute("totalPage", listByUser.getTotalPages());
		model.addAttribute("currentPage", page);
		List<Travel> listFavo = travel.getFavoriteTour();
		model.addAttribute("favoriteItems", listFavo);
		System.out.println(listFavo);
		String username = request.getRemoteUser();
		System.out.println(username);
		String day1 = request.getParameter("day");
		String end1 = request.getParameter("end");
		model.addAttribute("day",day1);
		model.addAttribute("end",end1);
		String[][] chartData;
		String[][] chartData1;
		if(ObjectUtils.isEmpty(day1) && ObjectUtils.isEmpty(end1)) {
			chartData = statsService.sp_getTotalTravelOneMonth();
			for(int i = 0 ; i<12; i++) {
				System.out.println(chartData[0][i]);
				System.out.println(chartData[1][i]);
				System.out.println("");
			}
			String text = "Thống kê tour 12 tháng";
			model.addAttribute("text",text);
			System.out.println(chartData);
			model.addAttribute("chartData",chartData);
		}
		else {
			chartData = booking.getTotalPriceFromTo(day1, end1);
			System.out.println(chartData);
			String text = "Thống kê doanh thu từ ngày " + day1+" đến ngày "+end1 ;
			model.addAttribute("text",text);
			model.addAttribute("chartData",chartData);

		}
		return "staff/index";
	}

	@GetMapping("/InYear")
	public String doGetFindAllByUserActivateInYear(Model model,HttpServletRequest request,
												   @RequestParam(name="page",defaultValue = "1") int page)  throws Exception {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		List<Account> list = accountService.findAll();
		model.addAttribute("listUser",list);
		Page<Account> listByUser = accountService.findAllByUserActivateInYear(page-1, SIZE);
		String title = "Danh sách khách hàng mới đăng ký trong năm";
		model.addAttribute("title",title);
		model.addAttribute("listUser1", listByUser.getContent());
		model.addAttribute("totalPage", listByUser.getTotalPages());
		model.addAttribute("currentPage", page);
		List<Travel> listFavo = travel.getFavoriteTour();
		model.addAttribute("favoriteItems", listFavo);
		System.out.println(listFavo);
		String username = request.getRemoteUser();
		System.out.println(username);
		String day1 = request.getParameter("day");
		String end1 = request.getParameter("end");
		model.addAttribute("day",day1);
		model.addAttribute("end",end1);
		String[][] chartData;
		String[][] chartData1;
		if(ObjectUtils.isEmpty(day1) && ObjectUtils.isEmpty(end1)) {
			chartData = statsService.sp_getTotalTravelOneMonth();
			for(int i = 0 ; i<12; i++) {
				System.out.println(chartData[0][i]);
				System.out.println(chartData[1][i]);
				System.out.println("");
			}
			String text = "Thống kê tour 12 tháng";
			model.addAttribute("text",text);
			System.out.println(chartData);
			model.addAttribute("chartData",chartData);
		}
		else {
			chartData = booking.getTotalPriceFromTo(day1, end1);
			System.out.println(chartData);
			String text = "Thống kê doanh thu từ ngày " + day1+" đến ngày "+end1 ;
			model.addAttribute("text",text);
			model.addAttribute("chartData",chartData);

		}
		return "staff/index";
	}
}
