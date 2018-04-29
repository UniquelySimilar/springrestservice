package com.tcoveney.springrestservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.springrestservice.dao.CustomerDao;
import com.tcoveney.springrestservice.model.Customer;

@RestController
@RequestMapping("/api/customer")
public class CustomerApiController {
	
	@Autowired
	private CustomerDao customerDao;
	
	@GetMapping("/")
	public List<Customer> index() {
		
		List<Customer> customers = customerDao.findAll();
		
		return customers;	
	}
	
	@GetMapping("/{id}")
	public Customer show(@PathVariable int id) {
		return customerDao.find(id);
	}
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public void store(@RequestBody Customer customer, HttpServletRequest request, HttpServletResponse response) {
		int newCustomerId = customerDao.insert(customer);
		response.addHeader( "Location", request.getRequestURL().append( Integer.toString(newCustomerId) ).toString() );
	}

}
