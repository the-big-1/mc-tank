package company18.mctank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MainControllerTest implements InitializingBean {

	@Autowired
	public WebApplicationContext wac;

	public MockMvc mockMvc;

	@Override
	public void afterPropertiesSet() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.wac)
				.apply(springSecurity())
				.build();
	}

    @Test
    void index() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isFound());
	}

	@Test
	void indexLogin() throws Exception{
		mockMvc.perform(get("/")).andExpect(status().isFound()).andExpect(redirectedUrl("/login"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void indexAdmin() throws Exception{
		mockMvc.perform(get("/")).andExpect(status().isFound()).andExpect(redirectedUrl("/overview"));
	}

	@Test
	@WithMockUser(roles = "MANAGER")
	void indexManager() throws Exception{
		mockMvc.perform(get("/")).andExpect(status().isFound()).andExpect(redirectedUrl("/cart"));
	}

	@Test
	@WithMockUser(roles = "CUSTOMER")
	void indexCustomer() throws Exception{
		mockMvc.perform(get("/")).andExpect(status().isFound()).andExpect(redirectedUrl("/account"));
	}
}