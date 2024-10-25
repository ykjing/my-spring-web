package com.ticketcenter.movie.data;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Movie{
	
	@Id
	private Long id;
	private String title;
	private String descp;
	private int runtime;
	private String language;
	private Date rlsDt;
	
}
