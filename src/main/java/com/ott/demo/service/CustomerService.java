package com.ott.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ott.demo.models.Customer;
import com.ott.demo.repository.CustomerRepository;
import java.util.*;

/**
 * Service class for managing customers.
 */
@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerById(long customerId) {
    	return customerRepository.findById(customerId);
	}
    
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
    	List<Customer> customerList = new ArrayList<>();
    	customerRepository.findAll().forEach(customerList::add);
		return customerList;
    }
    
    public Customer createCustomer(Customer customer) {
    	customerRepository.save(customer);
        return customer;
    }

    public Optional<Customer> updateCustomer(Customer customer) {
        return Optional.of(customerRepository
            .findById(customer.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(cus -> {
            	cus.setFirstName(customer.getFirstName());
            	cus.setLastName(customer.getLastName());
                return cus;
            });
    }

    public void deleteCustomer(Long id) {
    	Optional<Customer> customer = getCustomerById(id);
    	if(customer.isPresent()) {
    		customerRepository.delete(customer.get());
    	}
    }

}

