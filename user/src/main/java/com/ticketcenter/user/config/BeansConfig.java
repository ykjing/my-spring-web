package com.ticketcenter.user.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ticketcenter.user.secure.AuthFilter;
import com.ticketcenter.user.secure.CookieFilter;
import com.ticketcenter.user.secure.CookieService;
import com.ticketcenter.user.service.UserService;

@Configuration
public class BeansConfig {

	@Bean
	FilterRegistrationBean<CookieFilter> cookieFilter(CookieService cookieServ) {
		FilterRegistrationBean<CookieFilter> registBean = new FilterRegistrationBean<>();
		registBean.setFilter(new CookieFilter(cookieServ));
		registBean.addUrlPatterns("/user/*");
		registBean.setOrder(1);
		return registBean;
	}
	
	@Bean
	FilterRegistrationBean<AuthFilter> authFilter(UserService userServ, CookieService cookieServ) {
		FilterRegistrationBean<AuthFilter> registBean = new FilterRegistrationBean<>();
		registBean.setFilter(new AuthFilter(userServ, cookieServ));
		registBean.addUrlPatterns("/user/*");
		registBean.setOrder(2);
		return registBean;
	}
}
