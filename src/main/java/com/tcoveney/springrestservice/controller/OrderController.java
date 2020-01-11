package com.tcoveney.springrestservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcoveney.springrestservice.dao.OrderDao;
import com.tcoveney.springrestservice.model.Order;

@RestController
@RequestMapping("/api")
// "http://localhost:9000" - vue cli dev server
// "http://vue-client-for-spring-rest.localhost" - Apache2 virtualhost for vue client
@CrossOrigin(origins = {"http://localhost:9000", "http://vue-client-for-spring-rest.localhost"})
public class OrderController {
	private static final Logger logger = LogManager.getLogger(OrderController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private OrderDao orderDao;
	
	// TODO: Create OrderValidator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		//binder.addValidators(new OrderValidator());
	}
	
	@GetMapping("/orders/{id}")
	public Order show(@PathVariable int id) {
		return orderDao.find(id);
	}

}
