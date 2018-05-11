package com.tcoveney.springrestservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tcoveney.springrestservice.dao.CustomerDao;
import com.tcoveney.springrestservice.model.Customer;
import com.tcoveney.springrestservice.validator.CustomerValidator;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:9000")
public class CustomerApiController {
	private static final Logger logger = LogManager.getLogger(CustomerApiController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CustomerDao customerDao;

	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new CustomerValidator());
    }
	
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
	public void store(@RequestBody @Validated Customer customer, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			processValidationErrors(bindingResult, response);
		}
		else {
			int newCustomerId = customerDao.insert(customer);
			response.setStatus(201);
			response.addHeader( "Location", request.getRequestURL().append( Integer.toString(newCustomerId) ).toString() );
		}
	}
	
	@PutMapping("/")
	public void update(@RequestBody @Validated Customer customer, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			processValidationErrors(bindingResult, response);
		}
		else {
			customerDao.update(customer);
		}
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		customerDao.delete(id);
	}
	
	private void processValidationErrors(BindingResult bindingResult, HttpServletResponse response) {
		response.setStatus(400);
		//logger.debug(result.getAllErrors());
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

		for(FieldError fieldError : bindingResult.getFieldErrors()){
	        ObjectNode objectNode = mapper.createObjectNode();
			String message = messageSource.getMessage(fieldError.getCodes()[0], null, Locale.US);
	        objectNode.put(fieldError.getField(), message);
	        arrayNode.add(objectNode);
			//logger.debug(fieldError.getField() + ": " + message);
		}
		// Add errors list to response as JSON
		try {
			response.getWriter().write(arrayNode.toString());
			response.getWriter().flush();
		}
		catch(IOException ioe) {
			logger.error("Error writing to response", ioe);
		}
	}

}
