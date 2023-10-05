package com.simplilearn.sportyshoes.model;

import java.util.ArrayList;
import java.util.Date;

public class Order {
	
	public int orderId;
	public ArrayList<Shoe> shoes;
	public String productImage;
	public String userEmail;
	public int orderPrice;
	public Date createdOn;
	
	
	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(int orderId, ArrayList<Shoe> shoes, Date createdOn) {
		this.orderId = orderId;
		this.shoes = shoes;
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", shoes=" + shoes + ", createdOn=" + createdOn + "]";
	}
	
}
