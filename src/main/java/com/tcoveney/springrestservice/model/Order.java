package com.tcoveney.springrestservice.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	// REFERENCE: https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
	// TODO: Determine if this is still needed after removing the @OneToMany association in Customer.java
	//@JsonIgnore	// Eliminates infinite recursion error during Jackson serialization
	// TODO: Determine if I am gaining anything with this @ManyToOne JPA association
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "customer_id", nullable = false)
//	private Customer customer;
	
	@Column(name = "customer_id", nullable=false, updatable=false)
	private Integer customerId;
	
	@Column(name = "order_status")
	private int orderStatus;
	
	@Column(name = "order_date")
	private Date orderDate;
	
	@Column(name = "required_date")
	private Date requiredDate;
	
	@Column(name = "shipped_date")
	private Date shippedDate;
	
	@Column(name = "created_at", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date createdAt;
	
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date updatedAt;
	
//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (!(object instanceof Order )) return false;
//        return id != null && id.equals(((Order) object).getId());
//    }
// 
//    @Override
//    public int hashCode() {
//        return 31;
//    }

//	public Customer getCustomer() {
//		return customer;
//	}
//	
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}

	public Integer getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public java.util.Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(java.util.Date createdAt) {
		this.createdAt = createdAt;
	}

	public java.util.Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(java.util.Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", orderStatus=" + orderStatus + ", orderDate=" + orderDate 
				+ ", requiredDate=" + requiredDate + ", shippedDate=" + shippedDate + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
