package com.tcoveney.springrestservice.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcoveney.springrestservice.model.Customer;

@Repository
@Transactional
public class CustomerDaoHibernateImpl implements CustomerDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public List<Customer> findAll() {
		Session session = sessionFactory.getCurrentSession();
		
		Customer customer1 = new Customer();
		customer1.setId(1);
		customer1.setName("Customer1");

		Customer customer2 = new Customer();
		customer2.setId(2);
		customer2.setName("Customer2");
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);
		
		return customers;
	}

	@Override
	public Customer find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Customer customer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void truncateCustomerTable() {
		// TODO Auto-generated method stub

	}

}
