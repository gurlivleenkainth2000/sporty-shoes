package com.simplilearn.sportyshoes.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.simplilearn.sportyshoes.db.DB;
import com.simplilearn.sportyshoes.model.Order;
import com.simplilearn.sportyshoes.model.Shoe;
import com.simplilearn.sportyshoes.model.User;

@RestController
public class OrderController {

	DB db = DB.getInstance();
	
	@GetMapping("/place-order")
    public ModelAndView placeOrder(HttpSession session) {

		User user = (User)session.getAttribute("user");
		
		Order order = new Order();
		
		order.orderId = db.orderList.size()+1;
		order.shoes = new ArrayList<Shoe>();
		order.shoes.addAll(db.cartList);
		
		order.productImage = order.shoes.get(0).image;
		
		int total = 0;
    	for(Shoe shoe : db.cartList) {
    		total += shoe.price;
    	}
		
		order.orderPrice = total;
		order.createdOn = new Date();
		order.userEmail = user.email;
		
		db.orders.put(order.orderId, order);
		db.orderList.add(order);
		
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("success.html");
   		modelAndView.addObject("message", "Order Placed Successfully with ID: "+order.orderId);
    	
   		db.cartList.clear();
   		
    	return modelAndView;
    }
	
	@GetMapping("/orders")
    public ModelAndView fetchOrders(HttpSession session) {
		
		User user = (User)session.getAttribute("user");
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("orders.html");
    	modelAndView.addObject("user", user);
    	
    	
    	List<Order> orders = new ArrayList<Order>();
    	for(Order order : db.orderList) {
    		if(order.userEmail.equals(user.email)) {
    			orders.add(order);
    		}
    	}
    	
    	modelAndView.addObject("total", orders.size());
    	modelAndView.addObject("orders", orders);
    	
    	return modelAndView;
    }
	
	@GetMapping("/get-orders")
	public LinkedHashMap<String, Object> getSOrders() {
    	LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
    	response.put("code", 101);
    	response.put("message", db.orderList.size()+" Orders Fetched Successfully.");
    	response.put("shoes", db.orderList);
    	return response;
    }
	
	
}
