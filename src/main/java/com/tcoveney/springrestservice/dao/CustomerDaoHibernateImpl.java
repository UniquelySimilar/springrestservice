package com.tcoveney.springrestservice.dao;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tcoveney.springrestservice.model.Customer;
import com.tcoveney.springrestservice.model.Order;

@Repository
@Transactional
public class CustomerDaoHibernateImpl implements CustomerDao {
	private static final Logger logger = LogManager.getLogger(CustomerDaoHibernateImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<Customer> findAll() {
		Session session = sessionFactory.getCurrentSession();
		
		return session.createQuery("from Customer").list();
	}

	@Override
	public Customer find(int id) {
		Session session = sessionFactory.getCurrentSession();
		Customer customer = session.get(Customer.class, id);
		// Load associated orders (lazy loaded)
		customer.getOrders().size();
		
		return customer;
	}

	@Override
	public int insert(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		
		return (Integer)session.save(customer);
	}

	@Override
	public Customer update(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		
		customer.setUpdatedAt(new Date());
		
		return (Customer)session.merge(customer);
	}

	@Override
	public void delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Customer customer = new Customer();
		customer.setId(id);
		session.delete(customer);
	}
}
