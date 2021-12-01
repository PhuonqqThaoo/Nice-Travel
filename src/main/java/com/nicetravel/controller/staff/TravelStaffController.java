package com.nicetravel.controller.staff;

import java.util.List;

import javax.validation.Valid;

import com.nicetravel.entity.TravelTypes;
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
@RequestMapping("/staff/tour-du-lich")
public class TravelStaffController {

	@Autowired
	private TravelService travelService;

	private static final int SIZE = 4;

	@Autowired
	private TravelTypeService travelTypeService;

	@GetMapping("")
	public String quanLyTour(Model model,
							 @RequestParam(name="page",defaultValue = "1") int page,
							 @RequestParam(name="pageList",defaultValue = "1") int pageList) {
		Page<Travel> listByTravelInMonth = travelService.getTravelInMonth(page-1, SIZE);
		model.addAttribute("listByTravelInMonth", listByTravelInMonth.getContent());
		model.addAttribute("totalPage", listByTravelInMonth.getTotalPages());
		model.addAttribute("currentPageLike", page);
		Page<Travel> list = travelService.getFindAllByTravel(pageList-1, SIZE);
		model.addAttribute("list", list.getContent());
		model.addAttribute("totalPage2", list.getTotalPages());
		model.addAttribute("currentPage", page);
		List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
		model.addAttribute("listTravelType",listTravelType);
		model.addAttribute("travelRequest", new Travel());
		return "staff/quan-ly/tour-du-lich/QuanLy-TourDuLich";
	}

	@GetMapping("/edit")
	public String doGetEdit(@RequestParam("id") Integer id, Model model) {
		List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
		model.addAttribute("listTravelType",listTravelType);
		Travel travelRequest = travelService.findTravelById(id);
		model.addAttribute("travelRequest", travelRequest);
		return "staff/quan-ly/tour-du-lich/QuanLy-TourDuLich::#form";
	}

	@PostMapping("/edit")
	public String doPostEdit(Model model,
							 @Valid @ModelAttribute("travelRequest") Travel travelRequest, BindingResult result,
			RedirectAttributes redirect) {
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
		return "redirect:/staff/tour-du-lich";
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
		return "redirect:/staff/tour-du-lich";
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

		return "redirect:/staff/tour-du-lich";
	}
}
