package com.simplilearn.sportyshoes.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.simplilearn.sportyshoes.model.Order;
import com.simplilearn.sportyshoes.model.Shoe;
import com.simplilearn.sportyshoes.model.User;

public class DB {

	public static DB db = new DB();
	
	public LinkedHashMap<Integer, Shoe> shoes = new LinkedHashMap<Integer, Shoe>();
	public LinkedHashMap<String, User> users = new LinkedHashMap<String, User>();
	public LinkedHashMap<Integer, Order> orders = new LinkedHashMap<Integer, Order>();
	
	public ArrayList<User> usersList = new ArrayList<User>();
	public ArrayList<Shoe> shoesList = new ArrayList<Shoe>();
	public ArrayList<Shoe> cartList = new ArrayList<Shoe>();
	public ArrayList<Order> orderList = new ArrayList<Order>();
	
	void setUpUsers() {
		users.put("john@example.com", new User("John Watson", "john@example.com", "john123"));
		users.put("fionna@example.com", new User("Fionna Flynn", "fionna@example.com", "fionna123"));
		users.put("sia@example.com", new User("Sia Sen", "sia@example.com", "sia123"));
		
		for(String email : users.keySet()) {
			usersList.add(users.get(email));
		}
		
	}
	
	void setUpShoes() {
		shoes.put(101, new Shoe(101, "1.png", "BlueWave Running Shoes", "Sports", "7, 8, 9", 2000));
		shoes.put(201, new Shoe(201, "2.png", "Elegant Leather Loafers", "Formal", "7, 8, 9", 3000));
		shoes.put(301, new Shoe(301, "3.png", "NeoFlex Athletic Shoes", "Sports", "7, 8, 9, 10", 4500));
		shoes.put(401, new Shoe(401, "4.png", "PowerStride Training Shoes", "Sports", "6, 7, 8, 9", 6000));
		shoes.put(501, new Shoe(501, "5.png", "StreetRunner Urban Sneakers", "Sports", "7, 9", 4000));
		shoes.put(601, new Shoe(601, "6.png", "VentureHike Trail Shoes", "Sports", "5, 8, 9", 2500));
		shoes.put(701, new Shoe(701, "7.png", "EnduraGrip Sports Sneakers", "Sports", "4, 6, 9", 5000));
		shoes.put(801, new Shoe(801, "8.png", "LightStride Performance Shoes", "Sports", "7, 8", 1200));
		shoes.put(901, new Shoe(901, "9.png", "MaxFit Pro Sports Shoes", "Sports", "7, 8, 9, 10", 4700));
		shoes.put(111, new Shoe(111, "10.png", "RapidFlex Running Shoes", "Sports", "4, 5, 6", 2500));
		shoes.put(211, new Shoe(211, "11.png", "ZenFlex Sports Sneakers", "Sports", "7, 8, 9", 6000));
		shoes.put(311, new Shoe(311, "12.png", "TrailBlaze Adventure Shoes", "Sports", "5, 6, 9", 7500));
		
		for(int code : shoes.keySet()) {
			shoesList.add(shoes.get(code));
		}
	}
	
	private DB() {
		setUpUsers();
		setUpShoes();
	}
	
	public static DB getInstance() {
		return db;
	}
	
}
