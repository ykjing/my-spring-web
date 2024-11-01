package com.ticketcenter.user.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketcenter.user.secure.CodeUtils;

import jakarta.validation.Valid;

import com.ticketcenter.user.data.User;
import com.ticketcenter.user.data.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repos;
	
	@Autowired
	CodeUtils codeUtils;
	
	public User getById(String id) {
		return repos.findById(id).orElse(null);
	}
	
	public boolean verifyUser(String id, String pwd) {
		
		User u = repos.findById(id).orElse(null);
		if(u==null) return false;
		
		String hexVal = codeUtils.hexWithSalt(u.getSalt(), pwd);
		System.out.println(hexVal);
		System.out.println(u.getPassword());
		return hexVal.equals(u.getPassword())? true : false;
	}
	
	public User newUser(User user) {
		Pair< String, String> hexRes = codeUtils.generateHash(user.getPassword());
		user.setSalt(hexRes.getKey());
		user.setPassword(hexRes.getValue());
		return repos.save(user);
	}
	
	public String changePwd(String id, String pwd) {
		Pair< String, String> hexRes = codeUtils.generateHash(pwd);
		int res = repos.updatePwdById(hexRes.getKey(), hexRes.getValue(), id);
		return res==1? "Success.":"Filed.";
	}
	
}
