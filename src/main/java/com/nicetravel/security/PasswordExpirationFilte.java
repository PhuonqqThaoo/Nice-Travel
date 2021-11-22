package com.nicetravel.security;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Component
class PasswordExpirationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

//        if (isUrlExcluded(httpRequest)) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        System.out.println("PasswordExpirationFilter");
//
//        Customer customer = getLoggedInCustomer();
//
//        if (customer != null && customer.isPasswordExpired()) {
//            showChangePasswordPage(response, httpRequest, customer);
//        } else {
//            chain.doFilter(httpRequest, response);
//        }

    }

}
