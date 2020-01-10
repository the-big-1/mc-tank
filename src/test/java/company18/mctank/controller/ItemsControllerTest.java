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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class ItemsControllerTest implements InitializingBean {

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	@Override
	public void afterPropertiesSet() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.wac)
				.apply(springSecurity())
				.build();
	}

    @Test
    void index() throws Exception {
		mockMvc.perform(get("/items"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
    }


    /*
	@Test
	@WithMockUser("ADMIN")
	void index1() throws Exception {
		mockMvc.perform(get("/items"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/items-management"));
	}

	@Test
	@WithMockUser("MANAGER")
	void index2() throws Exception {
		mockMvc.perform(get("/items"))
				.andExpect(status().isOk())
				.andExpect(redirectedUrl("/items"));
	}

     */

    @Test
    void newItem() throws Exception {
		mockMvc.perform(get("/newItem"))
			.andExpect(status().isFound());
    }

    @Test
    void registerNew() throws Exception {
    }

    @Test
    void itemDetails() throws Exception {
		mockMvc.perform(get("/pump/{number}", 2))
				.andExpect(status().isFound());
    }

}