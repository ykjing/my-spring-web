package com.ticketcenter.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ticketcenter.user.secure.CodeUtils;
import com.ticketcenter.user.data.User;
import com.ticketcenter.user.data.UserRepository;
import com.ticketcenter.user.service.UserService;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application_test.properties")
class UserApplicationTests {

	@Value("${spring.application.name}")
	private String appName;
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository repos;
	
	@SpyBean
	private CodeUtils codeutils;
	
	@Test
	void contextLoads() {
		assertThat(appName).isEqualTo("UserService_test");
	}
	
	@Test
	void restGetById() throws Exception {
		
		mvc.perform(get("/user/a1").header("Authorization", "Basic YTE6YWFh"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("aaa"));
	}

	@Test
	void serviceNewUser() throws Exception {
		
		when(codeutils.randomString()).thenReturn("ghjkl12345");
		
		User u = new User();
		u.setId("4h");
		u.setPassword("testpwd");
		u.setName("testUser");
		service.newUser(u);
		
		u = repos.findById("4h").orElse(null);
		assertThat(u.getPassword()).isEqualTo("0a81a40821bfe1aec665235376c242ba0597688b");
	}
	
	@Test
	void serviceChangePwd() throws Exception {
		
		when(codeutils.randomString()).thenReturn("ghjkl12345");
		String bodyContent = "{ \"id\": \"b2\", \"password\": \"bbb\"}";
		mvc.perform(
				patch("/user/password").contentType(MediaType.APPLICATION_JSON)
									   .content(bodyContent)
									   .header("Authorization", "Basic YTE6YWFh"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().string("Success."));
		
		String s = service.changePwd("b2", "bbb");
		assertThat(s).isEqualTo("Success.");
		
		User u = repos.findById("b2").orElse(null);
		assertThat(u.getPassword()).isEqualTo("17f6f722117228674a61cf6f57da1bfae41fab61");
	}
	
	@Test
	void serviceVerifyUser() throws Exception {
		
		boolean valid = service.verifyUser("a1", "aaa");
		assertThat(valid).isEqualTo(true);
	}
}
