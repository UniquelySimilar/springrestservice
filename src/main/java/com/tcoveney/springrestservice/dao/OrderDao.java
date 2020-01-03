package com.tcoveney.springrestservice.dao;

import java.util.List;

import com.tcoveney.springrestservice.model.Order;

public interface OrderDao {
	public List<Order> findAll();
	public Order find(int id);
	public void insert(Order order);
	public Order update(Order order);
	public void delete(int id);
}
