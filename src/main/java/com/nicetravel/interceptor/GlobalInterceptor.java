package com.nicetravel.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nicetravel.service.TravelTypeService;


@Component
public class GlobalInterceptor implements HandlerInterceptor{
	private final TravelTypeService typeService;

	@Autowired
	public GlobalInterceptor(TravelTypeService typeService) {
		this.typeService = typeService;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		request.setAttribute("types", typeService.getAllTravelType());
	}
}
