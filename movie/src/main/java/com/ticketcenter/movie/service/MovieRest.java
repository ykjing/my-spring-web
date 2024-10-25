package com.ticketcenter.movie.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticketcenter.movie.bean.ResponseObj;
import com.ticketcenter.movie.data.Movie;

@RestController
@RequestMapping("/movie")
public class MovieRest {
	
	@Autowired
	private MovieService movieServ;
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Movie>> getAllMovie(Pageable pageable){
		return ResponseEntity.ok(movieServ.getAllMovie(pageable));
	}
	
	@GetMapping("/all/{rows}/{page}")
	public  List<Movie> getAllMovie(@PathVariable int rows, @PathVariable int page, @RequestParam(defaultValue="id") String sortAttr){
		return movieServ.getAllMovie( rows, page, sortAttr);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseObj<Movie>> getMovieById(@PathVariable Long id) {
		HttpHeaders headers = new HttpHeaders();
	    headers.add("test_header", "testtesttest");
		
		return new ResponseEntity<>(new ResponseObj<>(movieServ.getMovieById(id)), headers, HttpStatus.OK);
	}
	
	@PostMapping("/now_playing")
	public List<Movie> updateMovieXNowplaying() {
		return movieServ.updateMovieData("now_playing");
	}
	
	@PostMapping("/popular")
	public List<Movie> updateMovieXPopular() {
		return movieServ.updateMovieData("popular");
	}
	
	@PostMapping("/top_rated")
	public List<Movie> updateMovieXTopRated() {
		return movieServ.updateMovieData("top_rated");
	}

}
