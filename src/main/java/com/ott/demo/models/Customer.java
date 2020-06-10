package com.ott.demo.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="customer")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String firstName;
	private String lastName;

	protected Customer() {
	}

	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
	
	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s']", id, firstName, lastName);
	}
}
