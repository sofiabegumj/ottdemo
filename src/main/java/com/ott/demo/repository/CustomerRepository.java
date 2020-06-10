package com.ott.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ott.demo.models.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

	Optional<Customer> findById(String id);
	
	List<Customer> findAll();
}
