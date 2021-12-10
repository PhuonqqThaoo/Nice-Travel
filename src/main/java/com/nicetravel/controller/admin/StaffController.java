package com.nicetravel.controller.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.lowagie.text.DocumentException;
import com.nicetravel.custom.UserServices;
import com.nicetravel.entity.Provider;
import com.nicetravel.export.UserExcelExporter;
import com.nicetravel.export.UserPDFExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;

@Controller
@RequestMapping("/admin/thong-tin-nhan-vien")
public class StaffController { 

	private final AccountService accountService;

	private final UserServices service;

	private final BCryptPasswordEncoder passwordEncoder;

	private static final int SIZE = 4;

	@Autowired
	public StaffController(AccountService accountService, UserServices service, BCryptPasswordEncoder passwordEncoder) {
		this.accountService = accountService;
		this.service = service;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("")
	public String doGetIndex(HttpServletRequest request, Model model,
							 @RequestParam(name="page",defaultValue = "1") int page) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		Page<Account> list = accountService.findAllByStaffPageActive(page-1, SIZE);
		model.addAttribute("listStaff", list.getContent());
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("userRequest",new Account());
		model.addAttribute("text", "Tất cả thông tin nhân viên");
		return "/admin/nhan-vien/ThongTinNhanVien";
	}

	@GetMapping("/all")
	public String getAllUser(Model model, HttpServletRequest request,
							 @RequestParam(name="page",defaultValue = "1") int page){
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		Page<Account> list = accountService.findAllByStaffPage(page-1, SIZE);
		model.addAttribute("listStaff", list.getContent());
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("userRequest",new Account());
		model.addAttribute("text", "Tất cả thông tin khách hàng");
		return "/admin/nhan-vien/ThongTinNhanVien";
	}

	@GetMapping("/noActive")
	public String getAllUserNoActive(Model model, HttpServletRequest request,
									 @RequestParam(name="page",defaultValue = "1") int page){
		Account account = accountService.findAccountsByUsername(request.getRemoteUser());
		model.addAttribute("account", account);
		Page<Account> list = accountService.findAllByStaffPageNoActive(page-1, SIZE);
		model.addAttribute("listStaff", list.getContent());
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("userRequest",new Account());
		model.addAttribute("text", "Thông tin nhân viên không hoạt động");
		return "/admin/nhan-vien/ThongTinNhanVien";
	}

	@GetMapping("/edit")
	public String doGetEdit(@RequestParam("username") String username, Model model) {
		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "/admin/nhan-vien/ThongTinNhanVien::#form"; 
	}
	
	@PostMapping("/edit")
	public String doPostEdit(@Valid @ModelAttribute("userRequest") Account userRequest ,
							 BindingResult result,
							 RedirectAttributes redirect) {
		String errorMessage = null;
		Account account = accountService.findAccountsByUsername(userRequest.getUsername());

		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage ="Người dùng không hợp lệ";
			}else {
				if(!passwordEncoder.matches(account.getPassword(), userRequest.getPassword())){
					account.setPasswordChangedTime(new Date());
				}else {
					account.setPasswordChangedTime(account.getPasswordChangedTime());
				}
				userRequest.setImg(account.getImg());
				userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
				userRequest.setPasswordChangedTime(new Date());
				userRequest.setTravels(userRequest.getTravels());
				accountService.update(userRequest);
				String successMessage = "Người dùng " + userRequest.getFullname() + " đã cập nhật thành công";
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Không thể cập nhật người dùng " + userRequest.getFullname() + ", vui long thử lại sau!";
		}
		
		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}
		return "redirect:/admin/thong-tin-nhan-vien";
	}
	
	@GetMapping("/delete")
	public String doGetDeleted(@RequestParam(name="username",required = true)String username,
			RedirectAttributes redirect) {
		try {
			accountService.delete(username);
			String successMessage = "Người dùng " + username + " đã được xóa thành công!";
			redirect.addFlashAttribute("successMessage", successMessage);
		} catch (Exception e) {
			e.printStackTrace();
			redirect.addFlashAttribute("errorMessage ", "Không thể xóa người dùng, vui lòng thử lại sau!");
		}
		return "redirect:/admin/thong-tin-nhan-vien";
	}
	
	@PostMapping("/create")
	public String doPostCreate(@Valid @ModelAttribute("userRequest") Account userRequest,
			BindingResult result,
			RedirectAttributes redirect) {
		String errorMessage = null;
		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage ="Người dùng không hợp lệ";
			}else {
				userRequest.setImg("user.png");
				userRequest.setProvider(Provider.DATABASE);
				userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
				accountService.saveStaff(userRequest);
				String successMessage = "Người dùng " + userRequest.getFullname() + " đã được tạo thành công!";
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Cannot create user, please try again!";
		}
		
		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}
		
		return "redirect:/admin/thong-tin-nhan-vien";
	}

//	export excel

	@GetMapping("/staffs/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=staff_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Account> listUsers = accountService.findAllByStaff();

//		List<Account> list = accountService.findAllByStaff();

		UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

		excelExporter.export(response);
	}

	@GetMapping("/staff/export/pdf")
	public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=staff_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<Account> listUsers = service.listAll();

		UserPDFExporter exporter = new UserPDFExporter(listUsers);
		exporter.export(response);

	}

}
