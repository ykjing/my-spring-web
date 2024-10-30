package com.ticketcenter.seat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticketcenter.seat.data.Hall;
import com.ticketcenter.seat.data.HallRepository;
import com.ticketcenter.seat.data.HallSeat;
import com.ticketcenter.seat.data.HallSeatRepository;
//
//import jakarta.transaction.Transactional;


@Service
public class HallService {

	@Autowired
	HallRepository repos;
	
	@Autowired
	HallSeatRepository seatRepos;
	
	public List<Hall> getAll() {
		return repos.findAll();
	}
	
	public Hall getById(String id) {
		return repos.findById(id).orElse(null);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<HallSeat> newHall(Hall hall) {
		Hall res = repos.save(hall);
		String hallId = res.getId();
		int n = res.getSeatCnt();
		List<HallSeat> seatList = new ArrayList<HallSeat>();
		for(int i=1; i<=n; i++) {
			String seatId = String.format("%s%03d", hallId, i);
			
			HallSeat seat = new HallSeat(seatId, i, "basic", hallId);
			seatList.add(seat);
		}
		return seatRepos.saveAll(seatList);	
	}
	
}
