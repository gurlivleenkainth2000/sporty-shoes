package com.simplilearn.sportyshoes.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.simplilearn.sportyshoes.db.DB;
import com.simplilearn.sportyshoes.model.Shoe;
import com.simplilearn.sportyshoes.model.User;

@RestController
public class ShoeController {

	DB db = DB.getInstance();
	
	@GetMapping("/shoes")
    public ModelAndView fetchShoes(HttpSession session) {
    	    	
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("shoes.html");
    	modelAndView.addObject("shoes", db.shoesList);
    	
    	return modelAndView;
    }
	
	@GetMapping("/cart")
    public ModelAndView cart(HttpSession session) {
    	    	
		User user = (User)session.getAttribute("user");
		
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("cart.html");
    	
    	int total = 0;
    	for(Shoe shoe : db.cartList) {
    		total += shoe.price;
    	}
    	
    	modelAndView.addObject("total", total);
    	modelAndView.addObject("user", user);
	   	modelAndView.addObject("shoes", db.cartList);
    	
    	return modelAndView;
    }
	
	@GetMapping("/add-to-cart")
    public ModelAndView addToCart(@RequestParam(name = "id") Integer shoeId, HttpSession session) {
    	System.out.println("ID: "+shoeId);		
		
		db.cartList.add(db.shoes.get(shoeId));
		
		System.out.println(db.shoes.get(shoeId));
		
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("success.html");
   		modelAndView.addObject("message", "Shoe "+db.shoes.get(shoeId).name+" Added Successfully to Cart");
    	
    	return modelAndView;
    }
	
	
	@GetMapping("/view-shoe")
    public ModelAndView viewShoe(@RequestParam(name = "id") Integer shoeId, HttpSession session) {
				
		User user = (User)session.getAttribute("user");
		Shoe shoe = db.shoes.get(shoeId);
		
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("user", user);
    	modelAndView.addObject("shoe", shoe);
    	modelAndView.setViewName("shoe.html");
    	
    	return modelAndView;
    }
	
    @PostMapping("/add-shoe")
    public LinkedHashMap<String, Object> addShoe(
    		@RequestParam(name = "id") Integer id,
    		@RequestParam(name = "image") String image, 
    		@RequestParam(name = "name") String name, 
			@RequestParam(name = "category")String category, 
			@RequestParam(name = "sizes")String sizes,
			@RequestParam(name = "price")Integer price,
			HttpSession session
		) {
		    			
		Shoe shoe = new Shoe(id, image, name, category, sizes, price);
	
		db.shoes.put(id, shoe);
		db.shoesList.add(shoe);
		
		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
    	response.put("code", 101);
    	response.put("message", shoe.name+" Added Successfully.");
    	response.put("shoe", shoe);
    	return response;	
    }
    
    @PutMapping("/update-shoe")
    public LinkedHashMap<String, Object> updateShoe(
    		@RequestParam(name = "id") Integer id,
    		@RequestParam(name = "image") String image, 
    		@RequestParam(name = "name") String name, 
			@RequestParam(name = "category")String category, 
			@RequestParam(name = "sizes")String sizes,
			@RequestParam(name = "price")Integer price,
			HttpSession session
		) {
		    			
		Shoe shoe = new Shoe(id, image, name, category, sizes, price);
		
		db.shoes.put(id, shoe);
		
		for(int idx=0;idx<db.shoesList.size();idx++) {
			if(db.shoesList.get(idx).id == id) {
				db.shoesList.set(idx, shoe);
				break;
			}
		}
		
		
		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
    	response.put("code", 101);
    	response.put("message", shoe.name+" Updated Successfully.");
    	response.put("shoe", shoe);
    	return response;	
    }
    
    // localhost:9001/delete-shoe?id=101
    @DeleteMapping("/delete-shoe")
    public LinkedHashMap<String, Object> deleteShoe(@RequestParam(name = "id")Integer id) {
    	LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
    
    	db.shoes.remove(id);
    	
    	for(int idx=0;idx<db.usersList.size();idx++) {
			if(db.shoesList.get(idx).id == id) {
				db.shoesList.remove(idx);
				break;
			}
		}
    	
    	
    	response.put("code", 101);
    	response.put("message", "Shoe with ID "+id+" Deleted Successfully.");
    	return response;
    }
	
    @GetMapping("/get-shoes")
    public LinkedHashMap<String, Object> getShoes() {
    	LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
    	response.put("code", 101);
    	response.put("message", db.shoesList.size()+" Shoes Fetched Successfully.");
    	response.put("shoes", db.shoesList);
    	return response;
    }
	
    
}
