package com.ticketcenter.movie.bean;

import java.io.Serializable;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseObj<T> implements Serializable{
	
	private String message;
	private T data;
	
	public ResponseObj() {
	}
	
	public ResponseObj(T data) {
		this.data = data;
	}
	
	public ResponseObj(T data, String message) {
		this.data = data;
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}
}
