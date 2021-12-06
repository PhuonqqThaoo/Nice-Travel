package com.nicetravel.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/403")
    public String accessDeniedPage(){
        return "/error/403";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        String errorPage = "error"; // default

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // handle HTTP 404 Not Found error
                errorPage = "error/404";

            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                // handle HTTP 403 Forbidden error
                errorPage = "error/403";

            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // handle HTTP 500 Internal Server error
                errorPage = "error/500";

            } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()){
                errorPage = "error/405";
            }
            else if ((statusCode == HttpStatus.UNAUTHORIZED.value())){
                errorPage = "error/401";
            }
        }

        return errorPage;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
