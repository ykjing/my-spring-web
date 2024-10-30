package com.ticketcenter.seat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketcenter.seat.data.Hall;
import com.ticketcenter.seat.data.HallSeat;
import com.ticketcenter.seat.data.HallSeatRepository;
import com.ticketcenter.seat.service.HallSeatService;
import com.ticketcenter.seat.service.HallService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application_test.properties")
class SeatApplicationTests {

	@Value("${spring.application.name}")
	String appName;
	
	@Autowired
	HallSeatService hallSeatServ;
	
	@Autowired
	HallService hallServ;
	
	@SpyBean
	HallSeatRepository hallSeatrepos;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	void contextLoads() {
		assertThat(appName).isEqualTo("SeatService_test");
	}
	
	@Test
	void getHallSeatById() throws JsonProcessingException {
		
		HallSeat hallSeat = hallSeatServ.getById("a001");
		System.out.println(new ObjectMapper().writeValueAsString(hallSeat));
		assertThat(hallSeat).extracting("type").isEqualTo("basic");
	}
	
	@Test
	void getHallSeatByHall() {
		
		List<HallSeat> seats = hallSeatServ.getByHallId("A");
		assertThat(seats.size()).isEqualTo(30);
	}
	
	@Test
	void updateSeatType() throws Exception {

		mvc.perform(patch("/hallSeat/a001/type/table"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().string("Success."));
			
	}
	
	@Test
	void getHallById() {
		
		Hall hall = hallServ.getById("a");
		assertThat(hall).extracting("seatCnt").isEqualTo(30);
	}
	
	@Test
	void newHall() throws Exception {

		String bodyContent = "{ \"id\": \"d\" , \"seatCnt\" : 20 }";
		mvc.perform(post("/hall").contentType(MediaType.APPLICATION_JSON).content(bodyContent))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
		
		Hall hall = hallServ.getById("d");
		assertThat(hall).extracting("seatCnt").isEqualTo(20);
		
		List<HallSeat> seats = hallSeatServ.getByHallId("d");
		assertThat(seats.size()).isEqualTo(20);
	}
	
	@Test
	public void testNewHallFail() {
		
		when(hallSeatrepos.saveAll(ArgumentMatchers.anyList()))
			.thenThrow(new IllegalArgumentException("Test Exception"));
		
		Hall h = new Hall();
		h.setId("e");
		h.setSeatCnt(30);
		
		Assertions.assertThrows(
				IllegalArgumentException.class, 
				() -> { 
					hallServ.newHall(h); 
				});
		
		Hall hall = hallServ.getById("e");
		assertThat(hall).isNull();		
	}

}
