package com.tcoveney.springrestservice.controller;

import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.javafaker.Faker;
import com.tcoveney.springrestservice.dao.CustomerDao;
import com.tcoveney.springrestservice.model.Customer;

@Controller
@RequestMapping("/populate")
public class PopulateDatabaseController {
	private static final Logger logger = LogManager.getLogger(PopulateDatabaseController.class);
	
	@Autowired
	private CustomerDao customerDao;
	
	@GetMapping("/customers")
	public void populateCustomers() {
		// NOTE: Creating specific dao 'count' method not needed since this method will not be used often
		List<Customer> customers = customerDao.findAll();
		if (customers.size() > 0) {
			logger.warn("DB table 'customers' already contains " + customers.size() + " records.  Truncate table if want to repopulate.");
			return;
		}
		
		Faker faker = new Faker(new Locale("en-US"));
		Customer customer = new Customer();
		
		for(int i = 0; i < 1000; i++) {
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = firstName + "." + lastName + "@example.com";
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setStreet(faker.address().streetAddress());
			customer.setCity(faker.address().city());
			customer.setState(faker.address().state());
			customer.setZipcode(faker.address().zipCode());
			customer.setHomePhone("303-555-1212");
			customer.setEmail(email);
			
			this.customerDao.insert(customer);
		}
	}

}
