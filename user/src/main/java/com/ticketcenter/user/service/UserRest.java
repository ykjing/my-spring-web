package com.ticketcenter.user.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketcenter.user.data.User;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@RestController
@SecurityRequirement(name = "basicAuth") 
@RequestMapping(value="/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRest {

	@Autowired
	UserService userService;
	
	@GetMapping("/{id}")
	public User getById(@PathVariable String id) {
		return userService.getById( id);
	}
	
	@PostMapping
	public User newUser(@Valid @RequestBody User user) {
		return userService.newUser( user);
	}
	
	@PatchMapping("/password")
	public String changePwd(@Valid @RequestBody UserPwdDto userPwd) {
		return userService.changePwd( userPwd.getId(), userPwd.getPassword());
	}
	
}

class UserPwdDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message="\"id\" is required.")
	private String id;
	
	@NotNull(message="\"password\" is required.")
	@Size(max=20, message="Max length is 20.")
	private String password;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
