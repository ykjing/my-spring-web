package com.ticketcenter.seat.data;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class HallSeat {

	@Id
	@Size(min = 1, max = 10)
	private String id;
	
	private int num;
	
	private String type;
	
	private String hallId;
	
	public HallSeat() {
		
	}
	
	public HallSeat(String id, int num, String type, String hallId) {
		this.id = id;
		this.num = num;
		this.type = type;
		this.hallId = hallId;
	}
}
