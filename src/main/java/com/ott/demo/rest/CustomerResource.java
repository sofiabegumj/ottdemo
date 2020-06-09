package com.ott.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.ott.demo.models.Customer;
import com.ott.demo.rest.error.UnAuthorizedException;
import com.ott.demo.service.CustomerService;
import com.ott.demo.utils.SecurityUtil;

import javax.validation.Valid;
import java.util.*;

@RestController
@Validated
@RequestMapping("/api")
public class CustomerResource {

	@Autowired
	private CustomerService customerService;

	private void validateAuthorization(String basicAuth) {
		boolean check = SecurityUtil.isValidAuthorization(basicAuth);
		if (!check) {
			throw new UnAuthorizedException("Unauthorized Basic Auth");
		}
	}

	/**
	 * {@code GET /customer/id}.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
	 */
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> getCustomerByCustomerNumber(@PathVariable Long id,
			@RequestHeader("Authorization") String basicAuth) {
		validateAuthorization(basicAuth);
		Optional<Customer> customer = customerService.getCustomerById(id);
		if (!customer.isPresent()) {
			throw new UnAuthorizedException(String.format("The given customer id '%s' is not exist", id));
		}
		return new ResponseEntity<>(customer.get(), HttpStatus.OK);
	}

	/**
	 * {@code GET /customer} : get all customers.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         all Customers.
	 */
	@GetMapping("/customer")
	public ResponseEntity<List<Customer>> getAllCustomers(@RequestHeader("Authorization") String basicAuth) {
		validateAuthorization(basicAuth);
		List<Customer> customers = customerService.getAllCustomers();
		if (customers.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	/**
	 * {@code POST  /customer} : Creates a new customer.
	 * <p>
	 * 
	 * @param customer the customer to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new customer, or with status {@code 400 (Bad Request)} if
	 *         the login or email is already in use.
	 */
	@PostMapping("/customer")
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, UriComponentsBuilder builder,
			@RequestHeader("Authorization") String basicAuth) {
		validateAuthorization(basicAuth);
		Customer newCustomer = customerService.createCustomer(customer);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/customer/{id}").buildAndExpand(customer.getId()).toUri());
		return new ResponseEntity<>(newCustomer, headers, HttpStatus.CREATED);
	}

	/**
	 * {@code PUT /customer} : Updates an existing customer.
	 *
	 * @param customer the customer to update.
	 */
	@PutMapping("/customer")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer,
			@RequestHeader("Authorization") String basicAuth) {
		validateAuthorization(basicAuth);
		Optional<Customer> curCustomer = customerService.getCustomerById(customer.getId());
		if (!curCustomer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Customer updateCustomer = curCustomer.get();
		updateCustomer.setFirstName(customer.getFirstName());
		updateCustomer.setLastName(customer.getLastName());

		Optional<Customer> updatedCustomer = customerService.updateCustomer(customer);
		if (!updatedCustomer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<>(updatedCustomer.get(), HttpStatus.OK);
	}

	/**
	 * {@code DELETE /customer/:id} : delete the Customer.
	 * 
	 * @param id Id of the person to be deleted
	 * @return ResponseEntity
	 */
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<Void> deletePeople(@PathVariable Long id, @RequestHeader("Authorization") String basicAuth) {
		validateAuthorization(basicAuth);
		Optional<Customer> customer = customerService.getCustomerById(id);
		if (!customer.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		customerService.deleteCustomer(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
