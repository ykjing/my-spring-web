package com.ticketcenter.user.secure;

import com.ticketcenter.user.service.UserService;

import java.io.IOException;
import java.util.Base64;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class AuthFilter implements Filter{

	private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
	
	UserService userServ;
	
	CookieService cookieServ;
	
	public AuthFilter(UserService userServ, CookieService cookieServ) {
		this.userServ = userServ;
		this.cookieServ = cookieServ;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		logger.info("Do 2nd Filter");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		boolean valid = false;
		logger.info("Get userId from Thread Context: {}", ThreadContext.get("userId"));
		if(ThreadContext.containsKey("userId")) valid = true; 
		else {
			
			String authStr = req.getHeader("Authorization");	
			String signature = null;
			String userId = null;
			
			if(authStr != null && authStr.toLowerCase().startsWith("basic")) {
				
				String decodeAuth = new String(Base64.getDecoder().decode(authStr.substring(6)));
				signature = cookieServ.genCookie(decodeAuth);
				String[] user = decodeAuth.split(":");
				userId = user[0];
				valid = userServ.verifyUser(user[0], user[1]);
				logger.info("UserId from basic Auth: {}", userId);
			}
			
			if(valid) {
				String cookie = userId+"#"+signature;
				Cookie tCookie = new Cookie("tAuth",new String(Base64.getEncoder().encode(cookie.getBytes())));
				tCookie.setMaxAge(60*5);
				res.addCookie(tCookie);
				cookieServ.addUser( userId, signature);
			}
		}
		
		if(!valid) {
			
			logger.info("UnAuth.");
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		chain.doFilter(req, res);
	}

}
