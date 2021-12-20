package com.nicetravel.controller.customer;

import com.nicetravel.controller.admin.FileUploadUtil;
import com.nicetravel.custom.UserServices;
import com.nicetravel.entity.Account;
import com.nicetravel.security.auth.CustomOAuth2User;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/customer")
public class InformationCustomerController {

	private final AccountService accountService;

	private final UserServices userServices;

	private final PasswordEncoder passwordEncoder;


	@Autowired
	public InformationCustomerController(AccountService accountService, UserServices userServices, PasswordEncoder passwordEncoder) {
		this.accountService = accountService;
		this.userServices = userServices;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/information-customer")
	public String getInformationCustomer(Model model, HttpServletRequest request, Authentication authentication) {

		Account account = accountService.findAccountsByUsername(request.getRemoteUser()); // remote

		String username = null;

		if (account == null){
			CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
			Account accountOauth = accountService.findByEmail(oauth2User.getEmail());
			username = accountOauth.getUsername();
		}
		else {
			username = account.getUsername();
		}

		model.addAttribute("account", accountService.findAccountsByUsername(username));
		return "customer/InformationCustomer";
	}
	
	@GetMapping("/edit-information-customer")
	public String getEditInformationCustomer(HttpServletRequest request, Model model, Authentication authentication) {
//		Account account = accountService.findAccountsByUsername(request.getRemoteUser()); // remote

		String username = userServices.getUserName(request, authentication);
		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "/customer/EditInformationCustomer";
	}

	@PostMapping("/edit-information-customer")
	public String update(@Valid @ModelAttribute(name = "userRequest") Account userRequest,
						 BindingResult result,
						 RedirectAttributes redirect, @RequestParam("fileImage") MultipartFile multipartFile, HttpServletRequest request, Authentication authentication) {
		String username = userServices.getUserName(request, authentication);
		String errorMessage = null;

		Account account = accountService.findAccountsByUsername(username);
		String password = account.getPassword();
		System.out.println("password: " + account.getPassword());

		try {
			// check if userRequest is not valid
			if (result.hasErrors()) {
				errorMessage = "Tài khoản không hợp lệ";
				System.out.println(errorMessage);
				redirect.addFlashAttribute("errorMessage", errorMessage);
			} else {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				if (fileName.equals("") || fileName.length() == 0 || fileName == null){
					System.out.println("accountImg: " + account.getImg());
					account.setImg(account.getImg());
					account.setPassword(password);
				}
				else {
					account.setImg(fileName);
				}

				accountService.update(account);
				accountService.update(userRequest);

				System.out.println("Account update: " + account);

				String uploadDir = "photos/" + "accounts/" + userRequest.getUsername();

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
//				accountService.update(userRequest);
				String successMessage = "Tài khoản " + userRequest.getFullname() + " đã được cập nhật";
				redirect.addFlashAttribute("successMessage", successMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "Không thể cập nhật tài khoản " + userRequest.getFullname() + " , vui lòng thử lại!";
		}

		if (!ObjectUtils.isEmpty(errorMessage)) { // khong null
			redirect.addFlashAttribute("errorMessage", errorMessage);
		}
		return "redirect:/customer/information-customer";
	}

	@GetMapping("/change-password")
	public String getChangePassword(HttpServletRequest request, Model model, Authentication authentication) {
		Account account = accountService.findAccountsByUsername(request.getRemoteUser()); // remote

		String username = null;

		if (account == null){
			CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
			Account accountOauth = accountService.findByEmail(oauth2User.getEmail());
			username = accountOauth.getUsername();
		}
		else {
			username = account.getUsername();
		}

		Account userRequest = accountService.findAccountsByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "/customer/ChangePassword";
	}

	@PostMapping("/change-password")
	public String postChangePassword(HttpServletRequest request,
									 Model model, RedirectAttributes ra, Authentication authentication) throws Exception {

		Account account = accountService.findAccountsByUsername(request.getRemoteUser()); // remote

		String username = null;

		if (account == null){
			CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
			Account accountOauth = accountService.findByEmail(oauth2User.getEmail());
			username = accountOauth.getUsername();
		}
		else {
			username = account.getUsername();
		}


		Account acc = accountService.findAccountsByUsername(username);

		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");

		model.addAttribute("pageTitle", "Thay đổi mật khẩu đã hết hạn");

		if (oldPassword.equals(newPassword)) {
			ra.addFlashAttribute("message", "Mật khẩu mới của bạn phải khác mật khẩu cũ.");
			System.out.println("Mật khẩu mới của bạn phải khác mật khẩu cũ.");
			return "redirect:/customer/change-password";
		}

		if (!passwordEncoder.matches(oldPassword, acc.getPassword())) {
			ra.addFlashAttribute("message", "Mật khẩu cũ của bạn không chính xác.");
			System.out.println("Mật khẩu cũ của bạn không chính xác.");
			return "redirect:/customer/change-password";

		} else {
			userServices.changePassword(acc, passwordEncoder.encode(newPassword));
			request.logout();
			ra.addFlashAttribute("message", "Bạn đã đổi mật khẩu thành công. "
					+ "Vui lòng đăng nhập lại.");

			return "redirect:/login";
		}
	}

}
