package com.simplilearn.sportyshoes.model;

public class User {

	public String name;
	public String email;
	public String password;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + "]";
	}
	
}
