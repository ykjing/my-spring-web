package com.ticketcenter.movie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ticketcenter.movie.bean.ApiService;
import com.ticketcenter.movie.data.Movie;
import com.ticketcenter.movie.data.MovieRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;


@Service
public class MovieService {

	private final Logger logger = LoggerFactory.getLogger(Movie.class);
	
	@Value("${tmdb.auth}")
	private String tmdbAuth;
	
	@Value("${tmdb.url}")
	private String tmdbUrl;
	
	@Autowired
	private MovieRepository repos;

	@Autowired
	private ApiService apiService;
	
	public Page<Movie> getAllMovie(Pageable pageable){
		return repos.findAll(pageable);
	}
	
	public List<Movie> getAllMovie(int rows, int page, String sortAttr){
		Pageable pageable = PageRequest.of(page, rows, Sort.by(sortAttr));
		return repos.findAll(pageable).getContent();
	}
	
	public Movie getMovieById(Long id) {
		return repos.findById(id).orElse(null);
	}
	
	public Movie addMovie(Movie movie) {
		return repos.save(movie);
	}
	
	
	public List<Movie> updateMovieData(String catg){

		String url = tmdbUrl+"/"+catg;
		Map<String, String> headers = Map.of("Authorization", "Bearer "+tmdbAuth);
		Pair<Integer, String> res;
		try {
			res = apiService.makeGetApi(url, headers);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if(res.getKey()!=200) {
			logger.error("Call update movie API # Status Code: %d\n",res.getKey());
			return null;
		}
		
		JsonObject jsonObj = JsonParser.parseString(res.getValue()).getAsJsonObject();
		JsonArray jsonArr = jsonObj.get("results").getAsJsonArray();
		
		List<Movie> movieList = new ArrayList<Movie>();
		
		for(JsonElement e: jsonArr) {
			
			JsonObject obj = e.getAsJsonObject();
			Movie movie = new Movie();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
            movie.setId(obj.get("id").getAsBigInteger().longValue());
            movie.setDescp(obj.get("overview").getAsString());
            movie.setLanguage(obj.get("original_language").getAsString());
            movie.setTitle(obj.get("title").getAsString());
            try {
				movie.setRlsDt(df.parse(obj.get("release_date").getAsString()));
			} catch (ParseException e1) {
				logger.warn("Parsing Date Time Format Error. Format: [yyyy-MM-dd] , Data: [{}]", 
						obj.get("release_date").getAsString());
				e1.printStackTrace();
			}
            movieList.add(movie);
		}
		
		return repos.saveAll(movieList);
	}
}
