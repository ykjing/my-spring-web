package com.ticketcenter.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.springframework.hateoas.MediaTypes;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application_test.properties")
class UserApplicationTests {

	@Value("${spring.application.name}")
	private String appName;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	void contextLoads() {
		assertThat(appName).isEqualTo("UserService_test");
	}
	
	@Test
	void queryUserService() throws Exception {
		
		mvc.perform(get("/user/1"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaTypes.HAL_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("aaa"));
	}

}
