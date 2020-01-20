package com.tcoveney.springrestservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

import com.tcoveney.springrestservice.dao.OrderDao;
import com.tcoveney.springrestservice.exception.ResourceNotFoundException;
import com.tcoveney.springrestservice.model.Order;
import com.tcoveney.springrestservice.validator.ValidationUtils;

@RestController
@RequestMapping("/api/orders")
// "http://localhost:9000" - vue cli dev server
// "http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class OrderController {
	private static final Logger logger = LogManager.getLogger(OrderController.class);
	
	@Autowired
	private ValidationUtils validationUtils;
	
	@Autowired
	private OrderDao orderDao;
	
	// TODO: Create OrderValidator or use JSR-303 annotations in model
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		//binder.addValidators(new OrderValidator());
	}
	
	@GetMapping("/{id}")
	public Order find(@PathVariable int id) {
		Order order = orderDao.find(id);
		if (null == order) {
			throw new ResourceNotFoundException();
		}
		return order;
	}
	
	@PostMapping("/")
	public void insert(@RequestBody @Validated Order order, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		//logger.debug(order.toString());
		// NOTE: Since Order.customerID is NOT nullable and NOT updatable, it is ignored by this update
		if (bindingResult.hasErrors()) {
			// TODO: Implement
		}
		else {
			orderDao.insert(order);
			response.setStatus(201);
			//response.addHeader( "Location", request.getRequestURL().append( Integer.toString(newOrderId) ).toString() );
		}
	}
	
	@PutMapping("/")
	public void update(@RequestBody @Validated Order order, BindingResult bindingResult) {
		//logger.debug(order.toString());
		if (bindingResult.hasErrors()) {
			// TODO: Implement
		}
		else {
			orderDao.update(order);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		orderDao.delete(id);
	}

}
