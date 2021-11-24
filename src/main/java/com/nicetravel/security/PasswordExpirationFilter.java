package com.nicetravel.security;

import com.nicetravel.custom.CustomUserDetails;
import com.nicetravel.entity.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class PasswordExpirationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (isUrlExcluded(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        System.out.println("Bộ lọc hết hạn mật khẩu");

        Account account = getLoggedInCustomer();

        if (account != null && account.isPasswordExpired()) {
            showChangePasswordPage(response, httpRequest, account);
        } else {
            chain.doFilter(httpRequest, response);
        }

    }

    private boolean isUrlExcluded(HttpServletRequest httpRequest)
            throws IOException, ServletException {
        String url = httpRequest.getRequestURL().toString();

        if (url.endsWith(".css") || url.endsWith(".png") || url.endsWith(".js")
                || url.endsWith("/change-password")) {
            return true;
        }

        return false;
    }

    private Account getLoggedInCustomer() {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        Object principal = null;

        if (authentication != null) {
            principal = authentication.getPrincipal();
        }

        if (principal != null && principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return userDetails.getAccount();
        }

        return null;
    }

    private void showChangePasswordPage(ServletResponse response,
                                        HttpServletRequest httpRequest, Account account) throws IOException {
        System.out.println("Customer: " + account.getFullname() + " - Password Expired:");
        System.out.println("Last time password changed: " + account.getPasswordChangedTime());
        System.out.println("Current time: " + new Date());

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String redirectURL = httpRequest.getContextPath() + "/change-password";
        httpResponse.sendRedirect(redirectURL);
    }
}
