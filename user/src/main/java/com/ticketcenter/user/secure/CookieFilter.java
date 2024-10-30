package com.ticketcenter.user.secure;

import java.io.IOException;
import java.util.Base64;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(value=1)
@WebFilter(urlPatterns= {"/user/*"})
public class CookieFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CookieFilter.class);
	
	@Autowired
	CookieService cookieServ;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		logger.info("Do 1st Filter.");
		
		boolean valid = false;
		HttpServletRequest req = (HttpServletRequest) request;
		Cookie[] cookies = req.getCookies();
		String userId = null;
		
		if(cookies!=null) {
			for(Cookie c: cookies) {
				if(c.getName().equals("tAuth")) {
					
					logger.info("Find tAuth: {}", c.getValue());
					String decodeAuth = new String(Base64.getDecoder().decode(c.getValue()));
					int idx = decodeAuth.indexOf("#");
					userId = decodeAuth.substring(0,idx);
					String signature = decodeAuth.substring(idx+1);
					
					logger.info("Valid User By Cookie: {}", userId);
					valid = cookieServ.validUser( userId, signature);
					logger.info("Is cookie valid: {}", valid);
					break;
				}
			}
		}
		
		if(valid) ThreadContext.put("userId", userId);
		chain.doFilter(request, response);
	}

}
