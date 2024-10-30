package com.ticketcenter.seat.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface HallSeatRepository extends JpaRepository<HallSeat, String>{

	@Query("SELECT h FROM HallSeat h WHERE h.hallId = LOWER(?1)")
	List<HallSeat> findByHallId(String hallId);
	
	@Transactional
	@Modifying
	@Query("UPDATE HallSeat h SET h.type= ?1 WHERE h.id= ?2")
	int updateTypeById(String type, String id);
}
