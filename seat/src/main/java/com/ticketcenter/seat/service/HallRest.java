package com.ticketcenter.seat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketcenter.seat.data.Hall;
import com.ticketcenter.seat.data.HallSeat;

@RestController
@RequestMapping("/hall")
public class HallRest {

	@Autowired
	HallService hallserv;

	@GetMapping
	public List<Hall> getAll(){
		return hallserv.getAll();
	}
	
	@GetMapping("/{id}")
	public Hall getById(String id){
		return hallserv.getById(id);
	}
	
	@PostMapping
	public List<HallSeat> newHall(@RequestBody Hall hall) {
		return hallserv.newHall(hall);
	}
	
}
