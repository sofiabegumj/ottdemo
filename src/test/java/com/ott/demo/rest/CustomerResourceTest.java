package com.ott.demo.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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

	Optional<Customer> mockCustomer = Optional.of(new Customer("seven", "seven"));
	
	@Test
	public void getCustomerById() throws Exception {
		//Mockito.when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(mockCustomer);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/customer/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(404, status);
		System.out.println(result.getResponse().getContentAsString());
		String expected = "{id:4,firstName:three,lastName:three}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
    public void testAddCustomer() 
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
        Customer mockCustomer = new Customer("seven", "seven");
        ResponseEntity<Customer> responseEntity = customerResource.createCustomer("Basic dGVjaG5pY2FsOkFzc2Vzc21lbnQ=", mockCustomer, builder);
         
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

}
