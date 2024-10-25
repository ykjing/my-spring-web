package com.ticketcenter.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ticketcenter.movie.service.MovieService;

@EnableScheduling
@Component
public class MovieScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(MovieScheduler.class);
	
	@Autowired
	private MovieService service;
	
	@Scheduled(cron = "${jobs.schedule.cron:-}")
	public void updateMovieData() {
		service.updateMovieData("now_playing");
		service.updateMovieData("popular");
		service.updateMovieData("top_rated");
	}
}
