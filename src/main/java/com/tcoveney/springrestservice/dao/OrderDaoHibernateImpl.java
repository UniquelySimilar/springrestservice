package com.tcoveney.springrestservice.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tcoveney.springrestservice.model.Order;

public class OrderDaoHibernateImpl implements OrderDao {
	private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<Order> findAll() {
		Session session = sessionFactory.getCurrentSession();
		
		return session.createQuery("from Order").list();
	}

	@Override
	public Order find(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		return session.get(Order.class, id);
	}

	@Override
	public void insert(Order order) {
		Session session = sessionFactory.getCurrentSession();
		
		// NOTE: If I need the new order ID value, then call 'save()' instead
		session.persist(order);
	}

	@Override
	public Order update(Order order) {
		Session session = sessionFactory.getCurrentSession();
		
		return (Order)session.merge(order);
	}

	@Override
	public void delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Order order = new Order();
		order.setId(id);
		session.delete(order);
	}

}
