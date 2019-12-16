package company18.mctank.controller;

import company18.mctank.domain.Customer;
import company18.mctank.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.salespointframework.useraccount.UserAccountIdentifier;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Iterator;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.notNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WithMockUser(username = "username", roles={"ADMIN"})
class UserManagementControllerTest implements InitializingBean {

//	@Autowired
//	UserManagementController userManagementController;

	@Autowired
	CustomerService customerService;

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
    void customers() throws Exception {
    	mockMvc.perform(get("/user-management"))
				.andExpect(status().isOk())
				.andExpect(view().name("user-management"))
				.andExpect(model().attributeExists("customerList"));

    }

    @Test
    void disableAndEnableCustomer() throws Exception {
		Iterator<Customer> customerIterator = customerService.findAll().iterator();
		assertTrue(customerIterator.hasNext());
		Customer customer = customerIterator.next();
		long customerId = customer.getId();
		UserAccountIdentifier id = customer.getUserAccount().getId();

		mockMvc.perform(get("/customer/disable")
							.param("id", Objects.requireNonNull(id).toString()))
				.andExpect(status().isFound());

		mockMvc.perform(get("/customer/enable")
				.param("id", Objects.requireNonNull(id).toString()))
				.andExpect(status().isFound());
    }


    @Test
    void deleteCustomer() {
    }
}