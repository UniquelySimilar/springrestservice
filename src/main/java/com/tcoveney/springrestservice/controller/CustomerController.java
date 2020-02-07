package com.tcoveney.springrestservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.springrestservice.dao.CustomerDao;
import com.tcoveney.springrestservice.dao.OrderDao;
import com.tcoveney.springrestservice.exception.ResourceNotFoundException;
import com.tcoveney.springrestservice.model.Customer;
import com.tcoveney.springrestservice.model.CustomerWithOrders;
import com.tcoveney.springrestservice.model.Order;
import com.tcoveney.springrestservice.validator.CustomerValidator;
import com.tcoveney.springrestservice.validator.ValidationUtils;

@RestController
@RequestMapping("/api/customers")
// "http://localhost:9000" - vue cli dev server
// "http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class CustomerController {
	private static final Logger logger = LogManager.getLogger(CustomerController.class);

	@Autowired
	private ValidationUtils validationUtils;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private OrderDao orderDao;

	@GetMapping("/")
	public List<Customer> findAll() {
		
		List<Customer> customers = customerDao.findAll();
		
		return customers;	
	}

	@GetMapping("/orderby/lastname")
	public List<Customer> findAllOrderByLastName() {
		
		List<Customer> customers = customerDao.findAllOrderByLastName();
		
		return customers;	
	}
	
	@GetMapping("/{id}")
	public Customer find(@PathVariable int id) {
		Customer customer = customerDao.find(id);
		if (null == customer) {
			throw new ResourceNotFoundException();
		}
		return customer;
	}
	
	@GetMapping("/lastname/{lastName}")
	public List<Customer> findByLastName(@PathVariable String lastName) {
		return customerDao.findByLastName(lastName);
	}
	
	@GetMapping("/{customerId}/orders")
	public CustomerWithOrders findWithOrders(@PathVariable int customerId, HttpServletResponse response){
		CustomerWithOrders cwo = new CustomerWithOrders();
		Customer customer = customerDao.find(customerId);
		if (null == customer) {
			throw new ResourceNotFoundException();
		}
		else {
			List<Order> orders = orderDao.findByCustomer(customerId);
			cwo.setCustomer(customer);
			cwo.setOrders(orders);
		}
		return cwo;
	}
	
	@PostMapping(value = "/", headers = "content-type=application/json")
	public void insert(@RequestBody @Valid Customer customer, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			validationUtils.createValidationErrorsResponse(bindingResult, response);
		}
		else {
			int newCustomerId = customerDao.insert(customer);
			response.setStatus(201);
			response.addHeader( "Location", request.getRequestURL().append( Integer.toString(newCustomerId) ).toString() );
		}
	}
	
	@PutMapping(value = "/", headers = "content-type=application/json")
	public void update(@RequestBody @Valid Customer customer, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			validationUtils.createValidationErrorsResponse(bindingResult, response);
		}
		else {
			customerDao.update(customer);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		customerDao.delete(id);
	}

}
