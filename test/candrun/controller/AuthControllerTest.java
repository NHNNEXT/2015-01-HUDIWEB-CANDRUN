package candrun.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import candrun.support.enums.CommonInvar;
import candrun.support.enums.UserErrorcode;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:candrun-servlet.xml"})
public class AuthControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testLoginEmpty() throws Exception {
		mockMvc.perform(post("/auth").param("email", "asdf")
				.param("password", "asdf")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$." + CommonInvar.RETURNMSG.getValue()).value(UserErrorcode.EMPTY.getValue()));
	}
	
	@Test
	public void testLoginWrongPw() throws Exception {
		mockMvc.perform(post("/auth").param("email", "asdf0@asdf.net")
				.param("password", "asdf")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$." + CommonInvar.RETURNMSG.getValue()).value(UserErrorcode.WRONG_PW.getValue()));
	}
	
	@Test
	public void testLoginSuccess() throws Exception {
		mockMvc.perform(post("/auth").param("email", "asdf0@asdf.net")
				.param("password", "E876C917A5F6C564EF3AD701D8D1D46B")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$." + CommonInvar.RETURNMSG.getValue()).value(CommonInvar.SUCCESS.getValue()));
	}
}
