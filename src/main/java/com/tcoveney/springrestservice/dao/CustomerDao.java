package com.tcoveney.springrestservice.dao;

import java.util.List;

import com.tcoveney.springrestservice.model.Customer;

public interface CustomerDao {
	public List<Customer> findAll();
	public Customer find(int id);
	public List<Customer> findByLastName(String lastName);
	public int insert(Customer customer);
	public Customer update(Customer customer);
	public void delete(int id);
}
