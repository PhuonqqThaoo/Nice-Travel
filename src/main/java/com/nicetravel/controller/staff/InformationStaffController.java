package com.nicetravel.controller.staff;

import com.nicetravel.custom.CustomUserDetails;
import com.nicetravel.custom.UserServices;
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
@RequestMapping("/staff")
public class InformationStaffController {
    private final AccountService accountService;

    @Autowired
    UserServices userServices;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public InformationStaffController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/information-staff")
    public String getInformationAdmin(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        model.addAttribute("account", accountService.findAccountsByUsername(username));
        return "staff/ca-nhan/InformationStaff";
    }

    @GetMapping("/edit-information-staff")
    public String getEditInformationAdmin(HttpServletRequest request, Model model) {
        String username = request.getRemoteUser();
        Account userRequest = accountService.findAccountsByUsername(username);
        model.addAttribute("userRequest", userRequest);
        return "staff/ca-nhan/EditInformationStaff";
    }

    @PostMapping("/edit-information-staff")
    public String update(@Valid @ModelAttribute("userRequest") Account userRequest,
                         BindingResult result,
                         RedirectAttributes redirect, @RequestParam("img") MultipartFile multipartFile) throws IOException {
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
        return "redirect:/staff/information-staff";
//		return new RedirectView("/users", true);
    }

    @GetMapping("/change-password")
    public String getChangePassword(HttpServletRequest request, Model model) {
        String username = request.getRemoteUser();
        System.out.println("chang pass (user): " + username);
        Account userRequest = accountService.findAccountsByUsername(username);
        model.addAttribute("pageTitle", "Change Expired Password");
        model.addAttribute("userRequest", userRequest);

        return "staff/ca-nhan/ChangePassword";
    }

    @PostMapping("/change-password")
    public String postChangePassword(HttpServletRequest request,
                                     Model model, RedirectAttributes ra) throws Exception {

        Account acc = accountService.findAccountsByUsername(request.getRemoteUser());

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        model.addAttribute("pageTitle", "Thay đổi mật khẩu đã hết hạn");

        if (oldPassword.equals(newPassword)) {
            model.addAttribute("message", "Mật khẩu mới của bạn phải khác mật khẩu cũ.");
            System.out.println("Mật khẩu mới của bạn phải khác mật khẩu cũ.");
            return "redirect:/staff/change-password";
        }

        if (!passwordEncoder.matches(oldPassword, acc.getPassword())) {
            model.addAttribute("message", "Mật khẩu cũ của bạn không chính xác.");
            System.out.println("Mật khẩu cũ của bạn không chính xác.");
            return "redirect:/staff/change-password";
        } else {
            userServices.changePassword(acc, passwordEncoder.encode(newPassword));
            request.logout();
            ra.addFlashAttribute("message", "Bạn đã đổi mật khẩu thành công. "
                    + "Vui lòng đăng nhập lại.");

            return "redirect:/login";
        }
    }


}
