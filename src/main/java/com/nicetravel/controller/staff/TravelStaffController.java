package com.nicetravel.controller.staff;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.nicetravel.controller.admin.FileUploadUtil;
import com.nicetravel.entity.Account;
import com.nicetravel.entity.TravelTypes;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.TravelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nicetravel.entity.Travel;
import com.nicetravel.service.TravelService;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/staff/tour-du-lich")
public class TravelStaffController {

	@Autowired
	private TravelService travelService;

	private static final int SIZE = 4;

	@Autowired
	private TravelTypeService travelTypeService;

	@Autowired
	AccountService accountService;

	@GetMapping("")
	public String quanLyTour(Model model,
							 @RequestParam(name="page",defaultValue = "1") int page,
							 @RequestParam(name="pageList",defaultValue = "1") int pageList, HttpServletRequest request) {
		String username = request.getRemoteUser();
//		Travel travels = travelService.findTravelById();
//		model.addAttribute("travel", travels);
		model.addAttribute("account", accountService.findAccountsByUsername(username));
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
	public RedirectView doPostEdit(Model model,
								   @Valid @ModelAttribute("travelRequest") Travel travelRequest, BindingResult result,
								   RedirectAttributes redirect, @RequestParam("fileImage") MultipartFile multipartFile, HttpServletRequest request) {
		String errorMessage = null;
		Travel travel = travelService.findTravelById(travelRequest.getId());
		System.out.println("travel " + travel.toString());
		try {
			// check if travelRequest is not valid
			if (result.hasErrors()) {
				errorMessage = "Travel is not valid";
				redirect.addFlashAttribute("errorMessage", errorMessage);
			} else {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				System.out.println(fileName);
				if (fileName.equals("") || fileName.length() == 0 || fileName == null){
					System.out.println("accountImg: " + travel.getImg());
					travel.setImg(travel.getImg());
				}
				else {
					travel.setImg(fileName);
				}

				List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
				model.addAttribute("listTravelType",listTravelType);
				travelService.updateTraveladmin(travel);
				travelService.updateTraveladmin(travelRequest);

				String uploadDir = "photos/" + "travels/" + travelRequest.getId();
				System.out.println("dir: " + uploadDir);

				Path uploadPathTravel = Paths.get(uploadDir);

				if (!Files.exists(uploadPathTravel)) {
					Files.createDirectories(uploadPathTravel);
				}

				try (InputStream inputStream = multipartFile.getInputStream()) {
					Path filePath = uploadPathTravel.resolve(fileName);
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new IOException("Could not save upload file: " + fileName);
				}

				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				String successMessage = "Travel " + travelRequest.getName() + " was update";
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Cannot update travel" + travelRequest.getName() + ", please try again!";
		}

		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}
		return new RedirectView("/staff/tour-du-lich", true);
	}


	@PostMapping("/create")
	public String doPostCreate(@Valid @ModelAttribute("travelRequest") Travel travelRequest, BindingResult result,
			RedirectAttributes redirect,  @RequestParam("fileImage") MultipartFile multipartFile, HttpServletRequest request) {
		String errorMessage = null;
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage = "Travel is not valid";
			} else {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				System.out.println(fileName);

				travelRequest.setImg(fileName);
				travelRequest.setTravel_account_id(account);
				travelService.saveTravel(travelRequest);
				String uploadDir = "photos/" + "travels/" + travelRequest.getId();
				System.out.println("dir: " + uploadDir);

				Path uploadPathTravel = Paths.get(uploadDir);

				if (!Files.exists(uploadPathTravel)) {
					Files.createDirectories(uploadPathTravel);
				}

				try (InputStream inputStream = multipartFile.getInputStream()) {
					Path filePath = uploadPathTravel.resolve(fileName);
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new IOException("Could not save upload file: " + fileName);
				}

				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				String successMessage = "Travel " + travelRequest.getName() + " was create";
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
}
