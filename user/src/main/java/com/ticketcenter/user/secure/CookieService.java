package com.ticketcenter.user.secure;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

	private static final Logger logger = LoggerFactory.getLogger(CookieService.class);
	
	@Autowired
	CodeUtils codeUtils;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public void addUser(String userId, String cookie) {
		
		redisTemplate.opsForValue().set(userId, cookie);
		redisTemplate.expire( userId, 5, TimeUnit.MINUTES);
	}
	
	public boolean validUser(String userId, String signature) {
		
		String s = redisTemplate.opsForValue().get(userId);
		
		if(s==null || !s.equals(signature)) return false;
		
		redisTemplate.expire( userId, 5, TimeUnit.MINUTES);
		return true;
	}
	
	public String genCookie(String value) {
		
		return codeUtils.generateHash(value).getValue();
	}
	 
}
