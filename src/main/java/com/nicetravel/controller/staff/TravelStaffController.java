package com.nicetravel.controller.staff;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.nicetravel.controller.admin.FileUploadUtil;
import com.nicetravel.entity.Account;
import com.nicetravel.entity.TravelDetail;
import com.nicetravel.entity.TravelTypes;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.TravelDetailService;
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

	@Autowired
	private TravelDetailService travelDetailService;

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
				if (fileName.length() == 0){
					System.out.println("accountImg: " + travel.getImg());
					travelRequest.setImg(travel.getImg());
				}
				else {
					travelRequest.setImg(fileName);
				}

				List<TravelTypes> listTravelType = travelTypeService.findAllAdmin();
				model.addAttribute("listTravelType",listTravelType);
//				travelService.updateTraveladmin(travel);
				travelService.updateTraveladmin(travelRequest);

				String uploadDir;

				if(fileName.length() == 0){
					uploadDir = "photos/" + "travels/" + travelRequest.getId() + travel.getImg();
				}
				else {
					uploadDir = "photos/" + "travels/" + travelRequest.getId();
				}
				System.out.println("dir: " + uploadDir);
				Path uploadPathTravel = Paths.get(uploadDir);

				if (!Files.exists(uploadPathTravel)) {
					Files.createDirectories(uploadPathTravel);
				}

				try (InputStream inputStream = multipartFile.getInputStream()) {
					Path filePath = uploadPathTravel.resolve(fileName);
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					System.out.println(e.toString());
					throw new IOException("Could not save upload file: " + fileName);
				}

				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				String successMessage = "Tour " + travelRequest.getName() + " cập nhật thành công";
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Không thể cập nhật tour" + travelRequest.getName() + ", vui lòng thử lại!";
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

//	lich trinh

	@RequestMapping("/lich-trinh")
	public String getThongKeTourBooking(HttpServletRequest request, Model model) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		List<TravelDetail> travelDetailList = travelDetailService.getAllTravelDetail();
		model.addAttribute("listTravelDetail", travelDetailList);
		List<Travel> listTravelName = travelService.findAllTravelAdmin();
		model.addAttribute("listTravelName", listTravelName);
		model.addAttribute("travelDetailRequest", new TravelDetail());
		return "staff/quan-ly/tour-du-lich/Quanly-LichTrinh";
	}

	@PostMapping("/lich-trinh")
	public String createLichTrinh(@Valid @ModelAttribute("travelDetailRequest") TravelDetail travelDetail, BindingResult result, RedirectAttributes redirect)  throws UnsupportedEncodingException, MessagingException {
		String errorMessage = null;
		try{
			if(result.hasErrors()){
				errorMessage = "Đã xảy ra lỗi khi hủy tour, xin thử lại!";
			}
			else {
				travelDetailService.createTravelDetail(travelDetail);
//                model.addAttribute("successMessage", "Vui lòng kiểm tra email để xác nhận hủy tour");
				String successMessage = "Tạo lịch trình thành công.";
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		}catch(Exception e){
			e.printStackTrace();
			errorMessage = "Không thể cập nhật trạng thái cho lịch trình, xin thử lại!";
		}

		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}

		return "redirect:/staff/lich-trinh";
	}


	@GetMapping("/lich-trinh/edit")
	public String doGetEditTravelDetail(@RequestParam("id") Integer id, Model model) {
		TravelDetail travelDetailRequest = travelDetailService.findById(id);
		List<TravelDetail> listTravelDeTail = travelDetailService.getAllTravelDetail();
		List<Travel> listTravelName = travelService.findAllTravelAdmin();
		model.addAttribute("listTravelName", listTravelName);
		model.addAttribute("listTravelDeTail",listTravelDeTail);
		model.addAttribute("travelDetailRequest", travelDetailRequest);
		return "staff/quan-ly/tour-du-lich/Quanly-LichTrinh::#form";
	}

	@PostMapping("/lich-trinh/edit")
	public String doPostEdit(@Valid @ModelAttribute("travelDetailRequest") TravelDetail travelDetailRequest, BindingResult result,
							 RedirectAttributes redirect) {
		String errorMessage = null;

		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage = "Lịch trình không hợp lệ";
			} else {

				travelDetailRequest.setTime(travelDetailRequest.getTime());
				travelDetailRequest.setDescription(travelDetailRequest.getDescription());
				travelDetailRequest.setTravelId(travelDetailRequest.getTravelId());
				travelDetailRequest.setIsDeleted(travelDetailRequest.getIsDeleted());
				travelDetailService.updateTravelDetail(travelDetailRequest);
				String successMessage = "Lịch trình " + travelDetailRequest.getId() + " đã được cập nhật";
				redirect.addFlashAttribute("successMessage", successMessage);

			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Không thể cập nhật lịch trình có mã " + travelDetailRequest.getId() + ", Vui lòng thử lại!";
		}

		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}
		return "redirect:/staff/lich-trinh";
	}
}
