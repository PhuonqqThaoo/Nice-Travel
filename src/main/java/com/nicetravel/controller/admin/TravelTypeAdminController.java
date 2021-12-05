package com.nicetravel.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelTypes;
import com.nicetravel.service.TravelService;
import com.nicetravel.service.TravelTypeService;
import com.nicetravel.util.GenerateSlug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/travel-type")
public class TravelTypeAdminController {

	private final TravelTypeService travelTypeService;

	private final AccountService accountService;

	private static final int SIZE = 4;

	@Autowired
	public TravelTypeAdminController( AccountService accountService,TravelTypeService travelTypeService) {

		this.accountService = accountService;
		this.travelTypeService = travelTypeService;
	}
	
	@GetMapping("")
	public String getTravelType(Model model, HttpServletRequest request,
								@RequestParam(name="page",defaultValue = "1") int page) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		Page<TravelTypes> listTravelTypePage = travelTypeService.findAllAdminPage(page-1, SIZE);
		model.addAttribute("list", listTravelTypePage.getContent());
		model.addAttribute("totalPage", listTravelTypePage.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("travelTypeRequest", new TravelTypes());
		return "admin/quan-ly/tour-du-lich/QuanLy-TravelType";
	}

	@GetMapping("/edit")
	public String doGetEdit(@RequestParam("id") Integer id, Model model) {
		TravelTypes travelTypeRequest = travelTypeService.findById(id);
		model.addAttribute("travelTypeRequest", travelTypeRequest);
		return "admin/quan-ly/tour-du-lich/QuanLy-TravelType::#form";
	}

	@PostMapping("/edit")
	public String doPostEdit(@Valid @ModelAttribute("travelTypeRequest") TravelTypes travelTypeRequest, BindingResult result,
							 RedirectAttributes redirect, Model model) {
		String errorMessage = null;
		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage = "TravelType is not valid";
			} else {
//				String slug = GenerateSlug.toSlug(travelTypeRequest.getDescription());
//				travelTypeRequest.setSlug(slug);
				travelTypeService.updateTravelTypeAdmin(travelTypeRequest);
				String successMessage = "TravelType " + travelTypeRequest.getDescription() + " was update";
				System.out.println(travelTypeRequest.getDescription());
				System.out.println(travelTypeRequest.getSlug());
				redirect.addFlashAttribute("successMessage", successMessage);

			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Cannot update travel" + travelTypeRequest.getDescription() + ", please try again!";
		}

		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}
		return "redirect:/admin/travel-type";
	}

	@GetMapping("/delete")
	public String doGetDeleted(@RequestParam(name = "id", required = true) Integer id, RedirectAttributes redirect) {
		try {
			travelTypeService.deleteTravelTypeAdmin(id);
			String successMessage = "TravelType " + id + " was deleted!";
			redirect.addFlashAttribute("successMessage", successMessage);
		} catch (Exception e) {
			e.printStackTrace();
			redirect.addFlashAttribute("errorMessage", "Cannot delete travel, please try again!");
		}
		return "redirect:/admin/travel-type";
	}

	@PostMapping("/create")
	public String doPostCreate(@Valid @ModelAttribute("travelRequest") TravelTypes travelTypeRequest, BindingResult result,
							   RedirectAttributes redirect) {
		String errorMessage = null;
		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage = "Travel type is not valid";
			} else {
				travelTypeService.saveTravelType(travelTypeRequest);
				String successMessage = "Travel type " + travelTypeRequest.getDescription() + " was created!";
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Cannot create travel type, please try again!";
		}

		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}

		return "redirect:/admin/travel-type";
	}
}
