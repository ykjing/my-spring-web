package com.ticketcenter.user.data;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
@Entity
public class User {

	@Id
	@NotNull(message="\"id\" is required.")
	@Size(max=25, message="Max length is 25.")
	private String id;
	
	@NotNull(message="\"name\" is required.")
	@Size(max=50, message="Max length is 50.")
	private String name;
	private String salt;
	
	@NotNull(message="\"password\" is required.")
	@Size(max=20, message="Max length is 20.")
	private String password;
	private String email;
	private String phone;
	
	@CreationTimestamp(source = SourceType.DB)
	private Date createDt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty
	public Date getCreateDt() {
		return createDt;
	}

	@JsonIgnore
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}	
	
}
