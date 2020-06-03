package com.ott.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ott.demo.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findById(Long id);
	
	List<Customer> findAll();
}
