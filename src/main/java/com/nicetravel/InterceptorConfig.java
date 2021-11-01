package com.nicetravel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nicetravel.interceptor.GlobalInterceptor;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	@Autowired GlobalInterceptor globallInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(globallInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/rest/**","/admin/**","/assets/**");
	}
}
