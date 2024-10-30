package com.ticketcenter.seat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketcenter.seat.data.HallSeat;

@RestController
@RequestMapping("/hallSeat")
public class HallSeatRest {

	@Autowired
	HallSeatService serv;
	
	
	@GetMapping("/{id}")
	public HallSeat getById(@PathVariable String id) {
		return serv.getById(id);
	}
	
	@GetMapping("/hall/{id}")
	public List<HallSeat> getByHallId(@PathVariable String id) {
		return serv.getByHallId(id);
	}
	
	@PatchMapping("/{id}/type/{type}")
	public String updateTypeById(@PathVariable String id, String type) {
		return serv.updateTypeById(type, id);
	}
}
