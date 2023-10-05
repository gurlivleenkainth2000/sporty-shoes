package com.simplilearn.sportyshoes.model;

public class Shoe {

	public int id;
	public String image;
	public String name;
	public String category;
	public String sizes;
	public int price;

	public Shoe() {
		
	}
	
	public Shoe(int id, String image, String name, String category, String sizes, int price) {
		this.id = id;
		this.image = image;
		this.name = name;
		this.category = category;
		this.sizes = sizes;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Shoe [id=" + id + ", image=" + image + ", name=" + name + ", category=" + category + ", sizes="
				+ sizes + ", price=" + price + "]";
	}
	
}
