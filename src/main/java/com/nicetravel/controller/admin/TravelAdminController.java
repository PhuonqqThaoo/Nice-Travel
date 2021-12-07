package com.nicetravel.controller.admin;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.TravelTypes;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.TravelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nicetravel.entity.Travel;
import com.nicetravel.service.TravelService;

@Controller
@RequestMapping("/admin/tour-du-lich")
public class TravelAdminController {

	private final TravelService travelService;

	private final TravelTypeService travelTypeService;

	private final AccountService accountService;

	private static final int SIZE = 4;

	@Autowired
	public TravelAdminController(TravelService travelService, TravelTypeService travelTypeService, AccountService accountService) {
		this.travelService = travelService;
		this.travelTypeService = travelTypeService;
		this.accountService = accountService;
	}

	@GetMapping("")
	public String quanLyTour(Model model, HttpServletRequest request,
							 @RequestParam(name="page",defaultValue = "1") int page,
							 @RequestParam(name="pageList",defaultValue = "1") int pageList,Travel travelRequest) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		Page<Travel> listByTravelInMonth = travelService.getTravelInMonth(page-1, SIZE);
		model.addAttribute("listByTravelInMonth", listByTravelInMonth.getContent());
		model.addAttribute("totalPage", listByTravelInMonth.getTotalPages());
		model.addAttribute("currentPageLike", page);
		Page<Travel> list = travelService.getFindAllByTravelActive(pageList-1, SIZE);
//		Boolean isDeleted = travelRequest.getIsDeleted();
//		Boolean expires = travelRequest.getExpirationDate();
//		if (isDeleted == false && expires == false){
//			model.addAttribute("active");
//		}
		model.addAttribute("text", "Danh sách các tour du lịch đang hoạt động");
		System.out.println(list.getTotalPages());
		model.addAttribute("list", list.getContent());
		model.addAttribute("totalPage2", list.getTotalPages());
		model.addAttribute("currentPage", page);
		List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
		model.addAttribute("listTravelType",listTravelType);
		model.addAttribute("travelRequest", new Travel());
		return "admin/quan-ly/tour-du-lich/QuanLy-TourDuLich";
	}

	@GetMapping("/all")
	public String getTourAll(Model model, HttpServletRequest request,
							 @RequestParam(name="page",defaultValue = "1") int page,
							 @RequestParam(name="pageList",defaultValue = "1") int pageList,Travel travelRequest) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		Page<Travel> listByTravelInMonth = travelService.getTravelInMonth(page-1, SIZE);
		model.addAttribute("listByTravelInMonth", listByTravelInMonth.getContent());
		model.addAttribute("totalPage", listByTravelInMonth.getTotalPages());
		model.addAttribute("currentPageLike", page);
		Page<Travel> list = travelService.getFindAllByTravel(pageList-1, SIZE);
//		model.addAttribute("all");
		model.addAttribute("text", "Danh sách tất cả các tour du lịch");
		System.out.println(list.getTotalPages());
		model.addAttribute("list", list.getContent());
		model.addAttribute("totalPage2", list.getTotalPages());
		model.addAttribute("currentPage", page);
		List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
		model.addAttribute("listTravelType",listTravelType);
		model.addAttribute("travelRequest", new Travel());
		return "admin/quan-ly/tour-du-lich/QuanLy-TourDuLich";
	}

	@GetMapping("/nonActive")
	public String getTourNonActive(Model model, HttpServletRequest request,
							 @RequestParam(name="page",defaultValue = "1") int page,
							 @RequestParam(name="pageList",defaultValue = "1") int pageList,Travel travelRequest) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		Page<Travel> listByTravelInMonth = travelService.getTravelInMonth(page-1, SIZE);
		model.addAttribute("listByTravelInMonth", listByTravelInMonth.getContent());
		model.addAttribute("totalPage", listByTravelInMonth.getTotalPages());
		model.addAttribute("currentPageLike", page);

		Page<Travel> list = travelService.getFindAllByTravelNonActive(pageList-1, SIZE);
		Boolean checked = travelRequest.getIsDeleted();
//		if (checked == true){
//			model.addAttribute("isDeleted");
//		}
		model.addAttribute("text", "Danh sách các tour du lịch đã xóa");
		System.out.println(list.getTotalPages());
		model.addAttribute("list", list.getContent());
		model.addAttribute("totalPage2", list.getTotalPages());
		model.addAttribute("currentPage", page);
		List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
		model.addAttribute("listTravelType",listTravelType);
		model.addAttribute("travelRequest", new Travel());
		return "admin/quan-ly/tour-du-lich/QuanLy-TourDuLich";
	}

	@GetMapping("/expires")
	public String getTourExpires(Model model, HttpServletRequest request,
								   @RequestParam(name="page",defaultValue = "1") int page,
								   @RequestParam(name="pageList",defaultValue = "1") int pageList,Travel travelRequest) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		Page<Travel> listByTravelInMonth = travelService.getTravelInMonth(page-1, SIZE);
		model.addAttribute("listByTravelInMonth", listByTravelInMonth.getContent());
		model.addAttribute("totalPage", listByTravelInMonth.getTotalPages());
		model.addAttribute("currentPageLike", page);
		Page<Travel> list = travelService.getFindAllByTravelExpires(pageList-1, SIZE);
		Boolean checked = travelRequest.getExpirationDate();
//		if (checked == true){
//			model.addAttribute("expires");
//		}
		model.addAttribute("text", "Danh sách các tour du lịch đã hết hạn");
		System.out.println(list.getTotalPages());
		model.addAttribute("list", list.getContent());
		model.addAttribute("totalPage2", list.getTotalPages());
		model.addAttribute("currentPage", page);
		List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
		model.addAttribute("listTravelType",listTravelType);
		model.addAttribute("travelRequest", new Travel());
		return "admin/quan-ly/tour-du-lich/QuanLy-TourDuLich";
	}


	@GetMapping("/edit")
	public String doGetEdit(@RequestParam("id") Integer id, Model model) {
		Travel travelRequest = travelService.findTravelById(id);
		List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
		model.addAttribute("listTravelType",listTravelType);
		model.addAttribute("travelRequest", travelRequest);
		return "admin/quan-ly/tour-du-lich/QuanLy-TourDuLich::#form";
	}

	@PostMapping("/edit")
	public String doPostEdit(@Valid @ModelAttribute("travelRequest") Travel travelRequest, BindingResult result,
			RedirectAttributes redirect, Model model) {
		String errorMessage = null;
		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage = "Travel is not valid";
			} else {
				List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
				model.addAttribute("listTravelType",listTravelType);

				travelService.updateTraveladmin(travelRequest);
				travelService.sp_updateEXD2();
				String successMessage = "Travel " + travelRequest.getName() + " was update";
				redirect.addFlashAttribute("successMessage", successMessage);
				System.out.println(travelRequest.getDeparturePlace());
				System.out.println(travelRequest.getName());
				System.out.println(travelRequest.getSlug());
				System.out.println(travelRequest.getQuantityNew());
				System.out.println(travelRequest.getStartDate());
				System.out.println(travelRequest.getEndDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Cannot update travel" + travelRequest.getName() + ", please try again!";
		}

		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}
		return "redirect:/admin/tour-du-lich";
	}

	@GetMapping("/delete")
	public String doGetDeleted(@RequestParam(name = "id", required = true) Integer id, RedirectAttributes redirect) {
		try {
			travelService.deleteTravelAdmin(id);
			String successMessage = "Travel " + id + " was deleted!";
			redirect.addFlashAttribute("successMessage", successMessage);
		} catch (Exception e) {
			e.printStackTrace();
			redirect.addFlashAttribute("errorMessage", "Cannot delete travel, please try again!");
		}
		return "redirect:/admin/tour-du-lich";
	}

	@PostMapping("/create")
	public String doPostCreate(@Valid @ModelAttribute("travelRequest") Travel travelRequest, BindingResult result,
			RedirectAttributes redirect, HttpServletRequest request) {
		String errorMessage = null;
		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage = "Travel is not valid";
			} else {
				Account id = accountService.getIdByUser(request.getRemoteUser());
				travelRequest.setTravel_account_id(id);
				travelService.saveTravel(travelRequest);
				String successMessage = "Travel " + travelRequest.getName() + " was created!";
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Cannot create travel, please try again!";
		}

		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}

		return "redirect:/admin/tour-du-lich";
	}
}
