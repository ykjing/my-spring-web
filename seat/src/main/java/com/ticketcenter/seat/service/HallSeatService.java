package com.ticketcenter.seat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketcenter.seat.data.Hall;
import com.ticketcenter.seat.data.HallRepository;
import com.ticketcenter.seat.data.HallSeat;
import com.ticketcenter.seat.data.HallSeatRepository;

@Service
public class HallSeatService {
	
	@Autowired
	HallSeatRepository hallSeatRepos;
	
	public HallSeat getById(String id) {
		return hallSeatRepos.findById(id).orElse(null);
	}
	
	public List<HallSeat> getByHallId(String id) {
		return hallSeatRepos.findByHallId(id);
	}
	
	public String updateTypeById(String type, String id) {
		int res = hallSeatRepos.updateTypeById(type, id);
		return res==1? "Success.":"Failed.";
	}
}
