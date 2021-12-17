package com.nicetravel.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.nicetravel.custom.UserServices;
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

@Controller
@RequestMapping("/admin/tour-du-lich")
public class TravelAdminController {

	private final TravelService travelService;

	private final TravelTypeService travelTypeService;

	private final AccountService accountService;

	private final UserServices userServices;

	private static final int SIZE = 4;

	@Autowired
	public TravelAdminController(TravelService travelService, TravelTypeService travelTypeService, AccountService accountService, UserServices userServices) {
		this.travelService = travelService;
		this.travelTypeService = travelTypeService;
		this.accountService = accountService;
		this.userServices = userServices;
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
			RedirectAttributes redirect, Model model, @RequestParam("fileImage") MultipartFile multipartFile, HttpServletRequest request) {
		String errorMessage = null;

		Travel travel = travelService.findTravelById(travelRequest.getId());


		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage = "Travel is not valid";
			} else {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				if (fileName.equals("") || fileName.length() == 0 || fileName == null){
					System.out.println("accountImg: " + travel.getImg());
					travel.setImg(travel.getImg());
				}
				else {
					travel.setImg(fileName);
				}

				Account ids = accountService.getIdByUser(request.getRemoteUser());
				travelRequest.setTravel_account_id(ids);
				List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
				model.addAttribute("listTravelType",listTravelType);

				travelService.updateTraveladmin(travel);
				travelService.updateTraveladmin(travelRequest);
				travelService.sp_updateEXD2();
				travelService.updateEX();
				String uploadDir = "photos/" + "travels/" + travel.getId();

				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				try (InputStream inputStream = multipartFile.getInputStream()) {
					Path filePath = uploadPath.resolve(fileName);
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new IOException("Could not save upload file: " + fileName);
				}


				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


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
	public String doGetDeleted(@RequestParam(name = "id", required = true) Integer id, RedirectAttributes redirect, @RequestParam("fileImage") MultipartFile multipartFile, HttpServletRequest request) {
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
			RedirectAttributes redirect, HttpServletRequest request, @RequestParam("fileImage") MultipartFile multipartFile) {
		String errorMessage = null;
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		try {
			// check if userRequest is not valid
//			if(travel != null){
//				return errorMessage = "Travel tồn tại";
//			}
			 if (result.hasErrors()) {
				errorMessage = "Travel không hợp lệ";
			}
			else {

				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				System.out.println(fileName);

				travelRequest.setImg(fileName);
				travelRequest.setTravel_account_id(account);
				travelService.saveTravel(travelRequest);

				String uploadDir = "photos/" + "travels/" + travelRequest.getId();

				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				try (InputStream inputStream = multipartFile.getInputStream()) {
					Path filePath = uploadPath.resolve(fileName);
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new IOException("Could not save upload file: " + fileName);
				}


				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
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
