package com.tcoveney.springrestservice.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

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
	@SuppressWarnings("unchecked")
	public List<Customer> findAll() {
		Session session = sessionFactory.getCurrentSession();
		
		return session.createQuery("from Customer").setMaxResults(50).list();
	}

	@Override
	public Customer find(int id) {
		Session session = sessionFactory.getCurrentSession();
				
		return session.find(Customer.class, id);
	}

	@Override
	public int insert(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		
		return (Integer)session.save(customer);
	}

	@Override
	public void update(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}
}
