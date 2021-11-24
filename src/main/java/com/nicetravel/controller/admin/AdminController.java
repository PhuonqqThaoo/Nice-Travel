package com.nicetravel.controller.admin;

import com.nicetravel.custom.CustomUserDetails;
import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
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
    public String update(@Valid @ModelAttribute("userRequest") Account userRequest,
                         BindingResult result,
                         RedirectAttributes redirect, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String errorMessage = null;
        ;
        try {
            // check if userRequest is not valid
            if (result.hasErrors()) {
                errorMessage = "Tài khoản không hợp lệ";
                redirect.addFlashAttribute("errorMessage", errorMessage);
            } else {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                System.out.println(fileName);
                userRequest.setImg(fileName);
                System.out.println("get" + userRequest.getImg());

                accountService.update(userRequest);

                System.out.println(userRequest);

                String uploadDir = "user-photos/" + userRequest.getUsername();

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
        return "redirect:/admin/information-admin";
//		return new RedirectView("/users", true);
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
    public String postChangePassword(HttpServletRequest request, HttpServletResponse response,
                                     Model model, RedirectAttributes ra,
                                     @AuthenticationPrincipal Authentication authentication,@ModelAttribute("userRequest") Account account) throws ServletException {


//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        Account account = userDetails.getAccount();

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        System.out.println("new: " + newPassword);
        String encodeOldPassword = passwordEncoder.encode(newPassword);
        String encodeNewPassword = passwordEncoder.encode(newPassword);
        System.out.println("encodeNew: " + encodeNewPassword);
        System.out.println("oldPass: " + account.getPassword());
        model.addAttribute("pageTitle", "Thay đổi mật khẩu đã hết hạn");

//        if (encodeOldPassword.equals(encodeNewPassword)) {
//            model.addAttribute("message", "Mật khẩu mới của bạn phải khác mật khẩu cũ.");
//
//            return "redirect:/change-password";
//        }
//
//        if (!passwordEncoder.matches(encodeOldPassword, account.getPassword())) {
//            model.addAttribute("message", "Mật khẩu cũ của bạn không chính xác.");
//            return "redirect:/change-password";
//
//        } else {
            System.out.println(encodeNewPassword);
            accountService.changePassword(account, newPassword, passwordEncoder);
            request.logout();
            ra.addFlashAttribute("message", "Bạn đã đổi mật khẩu thành công. "
                    + "Vui lòng thử lại.");

            return "redirect:/login";
//        }
    }


}
