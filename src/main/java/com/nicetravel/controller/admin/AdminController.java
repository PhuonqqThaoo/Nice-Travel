package com.nicetravel.controller.admin;

import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import com.sun.deploy.net.DownloadException;
import net.bytebuddy.description.type.TypeList;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final AccountService accountService;

	public AdminController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping("/information-admin")
	public String getInformationAdmin(Model model, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("account", accountService.findAccountsByUsername(username));
		return "admin/ca-nhan/InformationAdmin";
	}
	
	@GetMapping("/edit-information-admin")
	public String getEditInformationAdmin(HttpServletRequest request, Model model) {
		String username = request.getRemoteUser();
		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);

		return "admin/ca-nhan/EditInformationAdmin";
	}

	@PostMapping("/edit-information-admin")
	public String update(@Valid @ModelAttribute("userRequest") Account userRequest ,
						 BindingResult result,
						 RedirectAttributes redirect, @RequestParam("image") MultipartFile multipartFile) {
		String errorMessage = null;;
		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage ="User is not valid";
				redirect.addFlashAttribute("errorMessage", errorMessage);
			}else {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				userRequest.setImg(fileName);
//				userRequest.setPassword("123");
//				accountService.update(userRequest);
				accountService.update(userRequest);
//				System.out.println("update" + update);
//				String uploadDir = "user-photos/" + redirect.getId();
//				System.out.println("upploadDir: " + uploadDir);
//				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				String successMessage = "User " + userRequest.getFullname() + " was update";
				System.out.println(successMessage);
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Cannot update user " + userRequest.getFullname()+" , please try again!";
		}

		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}
		return "redirect:/admin/information-admin";
	}

	@GetMapping("/change-password")
	public String getChangePassword(HttpServletRequest request, Model model) {
		String username = request.getRemoteUser();
		System.out.println("chang pass (user): " + username);
		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);

		return "admin/ca-nhan/ChangePassword";
	}
	
	@PostMapping("/change-password")
	public String postChangePassword(Locale locale,
									 @RequestParam("password") String password,
									 @RequestParam("oldpassword") String oldPassword) {

			Account account = accountService.findAccountsByUsername(
					SecurityContextHolder.getContext().getAuthentication().getName());

//			if (!accountService.checkIfValidOldPassword(account, oldPassword)) {
////				throw new InvalidOldPasswordException();
//			}
//			accountService.changeUserPassword(account, password);
		return "admin/ca-nhan/ChangePassword";
	}
	
	
}
