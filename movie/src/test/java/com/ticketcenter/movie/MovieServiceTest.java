package com.ticketcenter.movie;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.InjectMocks;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.ticketcenter.movie.bean.ApiService;
import com.ticketcenter.movie.data.Movie;
import com.ticketcenter.movie.data.MovieRepository;
import com.ticketcenter.movie.service.MovieService;


@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
	
	@InjectMocks
	MovieScheduler task;
	
	@InjectMocks
	MovieService movieService = Mockito.spy(MovieService.class);
	
	@Mock
	ApiService apiservice;
	
	@Mock
	MovieRepository repos;
	
	@BeforeEach()
	void mockInit() throws IOException, InterruptedException {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream is = classLoader.getResourceAsStream("movie_api.txt");
		String apiData = new String( is.readAllBytes(), StandardCharsets.UTF_8);
		when(apiservice.makeGetApi( ArgumentMatchers.anyString(), ArgumentMatchers.anyMap()))
			.thenReturn(new ImmutablePair<>(200, apiData));
		when(repos.saveAll(ArgumentMatchers.<Movie>anyList()))
			.thenAnswer(i->i.getArgument(0));
	}
	
	@Test
	void whenDoUpdateMovieData() {
		
		List<Movie> res = movieService.updateMovieData("popular");
		assertThat(res.get(0).getTitle()).isEqualTo("The Wild Robot");
	}
	
	@Test
	void whenSchduledTaskRun() throws IOException, InterruptedException {
		task.updateMovieData();
		verify(apiservice, times(3)).makeGetApi(ArgumentMatchers.anyString(), ArgumentMatchers.anyMap());
	}
	
}
