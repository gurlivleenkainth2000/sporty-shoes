package com.simplilearn.sportyshoes.controller;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.simplilearn.sportyshoes.db.DB;
import com.simplilearn.sportyshoes.model.User;


@RestController
public class AuthController {
	
	DB db = DB.getInstance();

    @PostMapping("/login")
	public ModelAndView login(
   			@RequestParam(name = "email")String email, 
   			@RequestParam(name = "password")String password,
   			HttpSession session
   		) {
   		
   		//LinkedHashMap<String, String> response = new LinkedHashMap<String, String>();
   		
		ModelAndView modelAndView = new ModelAndView();
		
    	User user = db.users.get(email);
   		if(user != null && user.password.equals(password)) {
   			// Add details of User in Session
   	   		session.setAttribute("user", user);
   	   		System.out.println(user);
   	   		modelAndView.setViewName("home.html");
   	   		modelAndView.addObject("user", user);
   	   		modelAndView.addObject("shoes", db.shoesList);
   		}else {
   			modelAndView.setViewName("error.html");
   	   		modelAndView.addObject("message", "Invalid User Credentials. Please Try Again..");
   		}
   		
   		return modelAndView;
   	}
    
    @GetMapping("/register")
    public ModelAndView register() {
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("register.html");
    	modelAndView.addObject("dateTimeStamp", new Date().toString());
    	return modelAndView;
    }
    
    @GetMapping("/edit-profile")
    public ModelAndView editProfile(HttpSession session) {
    	ModelAndView modelAndView = new ModelAndView();
    	
    	User user = (User)session.getAttribute("user");
    	modelAndView.setViewName("edit-profile.html");
   		modelAndView.addObject("user", user);
    	modelAndView.addObject("dateTimeStamp", new Date().toString());
    	
    	return modelAndView;
    }
    
    @GetMapping("/home")
    public ModelAndView home(HttpSession session) {
    	ModelAndView modelAndView = new ModelAndView();
    	User user = (User)session.getAttribute("user");
   		modelAndView.addObject("user", user);
	   	modelAndView.addObject("shoes", db.shoesList);
    	modelAndView.setViewName("home.html");
    	return modelAndView;
    }
    
    
    @GetMapping("/profile")
    public ModelAndView profile(HttpSession session) {
    	ModelAndView modelAndView = new ModelAndView();
    	User user = (User)session.getAttribute("user");
   		modelAndView.addObject("user", user);
    	modelAndView.setViewName("profile.html");
    	return modelAndView;
    }
    
    @PostMapping("/update-user-profile")
    public ModelAndView updateUserProfle(
			@RequestParam(name = "name") String name, 
			@RequestParam(name = "email")String email,
			@RequestParam(name = "actualEmail")String actualEmail,
			@RequestParam(name = "password")String password,
			HttpSession session
		) {
		    	
    	ModelAndView modelAndView = new ModelAndView();
		
		User user = new User(name, email, password);
	
		if(!email.equals(actualEmail)) {
			db.users.remove(actualEmail);
		}
		db.users.put(user.email, user);
		
		session.setAttribute("user", user);
		
		modelAndView.setViewName("success.html");
		modelAndView.addObject("message", user.name+" Updated Successfully.");
		
		return modelAndView;
	}
    
    @PostMapping("/register-user")
    public ModelAndView saveUser(
			@RequestParam(name = "name") String name, 
			@RequestParam(name = "email")String email, 
			@RequestParam(name = "password")String password,
			HttpSession session
		) {
		    	
    	ModelAndView modelAndView = new ModelAndView();
		
		User user = new User(name, email, password);
	
		db.users.put(user.email, user);
		
		session.setAttribute("user", user);
		
		modelAndView.setViewName("home.html");
		modelAndView.addObject("user", user);
		
		return modelAndView;
	}
    
    
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
    	
    	session.setAttribute("user", null);
    	
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("index.html");
    	return modelAndView;
    }
    
    @PostMapping("/add-user")
    public LinkedHashMap<String, Object> addUser(
    		@RequestParam(name = "name") String name, 
			@RequestParam(name = "email")String email, 
			@RequestParam(name = "password")String password,
			HttpSession session
		) {
		    			
		User user = new User(name, email, password);
	
		db.users.put(user.email, user);
		db.usersList.add(user);
		
		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
    	response.put("code", 101);
    	response.put("message", user.name+" Added Successfully.");
    	response.put("user", user);
    	return response;	
    }
    
    @PostMapping("/update-user")
    public LinkedHashMap<String, Object> updateUser(
    		@RequestParam(name = "name") String name, 
			@RequestParam(name = "email")String email, 
			@RequestParam(name = "password")String password,
			HttpSession session
		) {
		    			
		User user = new User(name, email, password);
	
		db.users.put(user.email, user);
		
		for(int idx=0;idx<db.usersList.size();idx++) {
			if(db.users.get(idx).email.equals(email)) {
				db.usersList.set(idx, user);
				break;
			}
		}
		
		
		LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
    	response.put("code", 101);
    	response.put("message", user.name+" Updated Successfully.");
    	response.put("user", user);
    	return response;	
    }
    
    @GetMapping("/delete-user")
    public LinkedHashMap<String, Object> deleteUser(@RequestParam(name = "email")String email) {
    	LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
    
    	db.users.remove(email);
    	
    	for(int idx=0;idx<db.usersList.size();idx++) {
			if(db.users.get(idx).email.equals(email)) {
				db.usersList.remove(idx);
				break;
			}
		}
    	
    	
    	response.put("code", 101);
    	response.put("message", "User with Email: "+email+" Deleted Successfully.");
    	response.put("users", db.usersList);
    	return response;
    }
    
    @GetMapping("/get-users")
    public LinkedHashMap<String, Object> getUsers() {
    	LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
    	response.put("code", 101);
    	response.put("message", db.usersList.size()+" Users Fetched Successfully.");
    	response.put("users", db.usersList);
    	return response;
    }
	
}
