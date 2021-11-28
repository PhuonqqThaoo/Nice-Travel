package com.nicetravel.controller.admin;

import com.nicetravel.custom.CustomUserDetails;
import com.nicetravel.custom.UserServices;
import com.nicetravel.entity.Account;
import com.nicetravel.service.AccountService;
import com.nicetravel.service.RoleService;
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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AccountService accountService;

    private PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final UserServices userServices;

    @Autowired
    public AdminController(AccountService accountService, RoleService roleService, UserServices userServices) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.userServices = userServices;
    }

    @GetMapping("/information-admin")
    public String getInformationAdmin(Model model, HttpServletRequest request) {
        Account account = accountService.findAccountsByUsername(request.getRemoteUser());
        model.addAttribute("account", account);
        String username = request.getRemoteUser();
        model.addAttribute("account", accountService.findAccountsByUsername(username));
        return "admin/ca-nhan/InformationAdmin";
    }

    @GetMapping("/edit-information-admin")
    public String getEditInformationAdmin(HttpServletRequest request, Model model) {
        Account account = accountService.findAccountsByUsername(request.getRemoteUser());
        model.addAttribute("account", account);
        String username = request.getRemoteUser();
        Account userRequest = accountService.findAccountsByUsername(username);
        model.addAttribute("userRequest", userRequest);
        return "admin/ca-nhan/EditInformationAdmin";
    }

    @PostMapping("/edit-information-admin")
    public String update(@Valid @ModelAttribute(name = "userRequest") Account userRequest,
                         BindingResult result,
                         RedirectAttributes redirect, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {

        String errorMessage = null;

        try {
            // check if userRequest is not valid
            if (result.hasErrors()) {
                errorMessage = "Tài khoản không hợp lệ";
                System.out.println(errorMessage);
                redirect.addFlashAttribute("errorMessage", errorMessage);
            } else {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                System.out.println(fileName);
                userRequest.setImg(fileName);
                System.out.println("getImg" + userRequest.getImg());

                userRequest.setRole_Id(roleService.findByRoleName("USER"));
                System.out.println(userRequest.getRole_Id());

                accountService.update(userRequest);
                System.out.println("image: " + userRequest.getImg());

                System.out.println("userRequest: " + userRequest);

                String uploadDir = "user-photos/" + userRequest.getUsername();

                Path uploadPath = Paths.get(uploadDir);

                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                try(InputStream inputStream = multipartFile.getInputStream()){
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }catch(IOException e){
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
        return "redirect:/admin/information-admin";
//		return new RedirectView("/users", true);
    }

    @GetMapping("/change-password")
    public String getChangePassword(HttpServletRequest request, Model model) {
        Account account = accountService.findAccountsByUsername(request.getRemoteUser());
        model.addAttribute("account", account);
        String username = request.getRemoteUser();
        System.out.println("chang pass (user): " + username);
        Account userRequest = accountService.findAccountsByUsername(username);
        model.addAttribute("pageTitle", "Change Expired Password");
        model.addAttribute("userRequest", userRequest);

        return "admin/ca-nhan/ChangePassword";
    }

    @PostMapping("/change-password")
    public String postChangePassword(HttpServletRequest request, HttpServletResponse response,
                                     Model model, RedirectAttributes ra,
                                     @AuthenticationPrincipal Authentication authentication) throws Exception {


        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        System.out.println("userDetails" + userDetails);
        Account account = userDetails.getAccount();
        System.out.println("account" + account);

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        System.out.println("new: " + newPassword);
        String encodeOldPassword = passwordEncoder.encode(newPassword);
        String encodeNewPassword = passwordEncoder.encode(newPassword);
        System.out.println("encodeNew: " + encodeNewPassword);
        System.out.println("oldPass: " + oldPassword);
        model.addAttribute("pageTitle", "Thay đổi mật khẩu đã hết hạn");

        if (oldPassword.equals(newPassword)) {
            model.addAttribute("message", "Mật khẩu mới của bạn phải khác mật khẩu cũ.");

            return "redirect:/admin/change-password";
        }

//        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
//            model.addAttribute("message", "Mật khẩu cũ của bạn không chính xác.");
//            return "redirect:/admin/change-password";
//
//        }
        else {
            userServices.changePassword(account, encodeNewPassword);
            request.logout();
            ra.addFlashAttribute("message", "Bạn đã đổi mật khẩu thành công. "
                    + "Vui lòng thử lại.");

            return "redirect:/login";
        }
    }


}
