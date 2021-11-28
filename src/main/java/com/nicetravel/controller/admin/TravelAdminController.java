package com.nicetravel.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.TravelTypes;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.TravelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public TravelAdminController(TravelService travelService, TravelTypeService travelTypeService, AccountService accountService) {
		this.travelService = travelService;
		this.travelTypeService = travelTypeService;
		this.accountService = accountService;
	}

	@GetMapping("")
	public String quanLyTour(Model model, HttpServletRequest request) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		List<Travel> list = travelService.getFindAllByTravel();
		List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
		model.addAttribute("listTravelType",listTravelType);
		model.addAttribute("list", list);
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
			RedirectAttributes redirect) {
		String errorMessage = null;
		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage = "Travel is not valid";
			} else {
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
