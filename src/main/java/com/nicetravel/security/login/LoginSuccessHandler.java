package com.nicetravel.security.login;

import com.nicetravel.custom.CustomUserDetails;
import com.nicetravel.entity.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Account account;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String redirectURL = request.getContextPath();


//        if (userDetails.hasRole("ADMIN")) {
//            redirectURL = "/admin";
//        } else if (userDetails.hasRole("STAFF")) {
//            redirectURL = "/";
//        } else if (userDetails.hasRole("USER")) {
//            redirectURL = "/";
//        }

        response.sendRedirect(redirectURL);

    }
}
