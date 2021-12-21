package com.nicetravel.controller.admin;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.nicetravel.entity.Travel;
import com.nicetravel.entity.TravelDetail;
import com.nicetravel.service.TravelDetailService;
import com.nicetravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TravelDetailsAdminController {
	private final AccountService accountService;

	private final TravelService travelService;

	private final TravelDetailService travelDetailService;

	@Autowired
	public TravelDetailsAdminController(AccountService accountService, TravelService travelService, TravelDetailService travelDetailService) {
		this.accountService = accountService;
		this.travelService = travelService;
		this.travelDetailService = travelDetailService;
	}
	
	@RequestMapping("/lich-trinh")
	public String getThongKeTourBooking(HttpServletRequest request, Model model) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		List<TravelDetail> travelDetailList = travelDetailService.getAllTravelDetail();
		model.addAttribute("listTravelDetail", travelDetailList);
		List<Travel> listTravelName = travelService.findAllTravelAdmin();
		model.addAttribute("listTravelName", listTravelName);
		model.addAttribute("travelDetailRequest", new TravelDetail());
		return "admin/quan-ly/tour-du-lich/Quanly-LichTrinh";
	}

	@PostMapping("/lich-trinh")
	public String createLichTrinh(@Valid @ModelAttribute("travelDetailRequest") TravelDetail travelDetail, BindingResult result, RedirectAttributes redirect)  throws UnsupportedEncodingException, MessagingException  {
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

		return "redirect:/admin/lich-trinh";
	}


	@GetMapping("/lich-trinh/edit")
	public String doGetEdit(@RequestParam("id") Integer id, Model model) {
		TravelDetail travelDetailRequest = travelDetailService.findById(id);
		List<TravelDetail> listTravelDeTail = travelDetailService.getAllTravelDetail();
		List<Travel> listTravelName = travelService.findAllTravelAdmin();
		model.addAttribute("listTravelName", listTravelName);
		model.addAttribute("listTravelDeTail",listTravelDeTail);
		model.addAttribute("travelDetailRequest", travelDetailRequest);
		return "admin/quan-ly/tour-du-lich/Quanly-LichTrinh::#form";
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
		return "redirect:/admin/lich-trinh";
	}
}
