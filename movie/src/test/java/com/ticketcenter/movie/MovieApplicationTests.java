package com.ticketcenter.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.ticketcenter.movie.data.Movie;
import com.ticketcenter.movie.service.MovieService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application_test.properties")
class MovieApplicationTests {

	@Value("${tmdb.auth}")
	private String tmdbAuth;

    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private MovieService serv;
	
	@Test
	void contextLoads() {
		assertThat(tmdbAuth).isNotNull();
	}
	
	@Test
	void queryMovieService() {
		Movie movie = serv.getMovieById(155L);
		assertThat(movie).extracting("title").isEqualTo("The Dark Knight");
	}
	
	@Test
	void getMovieRest() throws Exception {
	    
		mvc.perform(get("/movie/155"))
	    	.andDo(MockMvcResultHandlers.print())
	    	.andExpect(status().isOk())
	    	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.data.title").value("The Dark Knight"));	
	}
	
}
