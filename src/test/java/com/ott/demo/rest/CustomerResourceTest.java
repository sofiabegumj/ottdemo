package com.ott.demo.rest;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ott.demo.models.Customer;
import com.ott.demo.service.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerResource.class)
public class CustomerResourceTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerResource customerResource;

	@MockBean
	private CustomerService customerService;

	@Test
	public void getCustomerById() throws Exception {
		Optional<Customer> mockCustomer = Optional.of(new Customer("one", "one"));
		Mockito.when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(mockCustomer);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/customer/1").accept(MediaType.APPLICATION_JSON)
		    .header("Authorization", "Basic dGVjaG5pY2FsOkFzc2Vzc21lbnQ=");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = result.getResponse().getContentAsString();
		String expected = "";
		assertEquals(content, expected);
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	@Test
	public void createCustomer() throws Exception {
		Customer cus = new Customer("nine", "nine");
		Mockito.when(customerService.createCustomer(cus)).thenReturn(cus);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/customer")
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic dGVjaG5pY2FsOkFzc2Vzc21lbnQ=")
				.content(mapToJson(cus));
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		
		String content = result.getResponse().getContentAsString();
		String expected = "";
		assertEquals(content, expected);
	}
}
