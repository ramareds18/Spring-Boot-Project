package com.example.mobileplaceorder.service.dto;

public class ProductDto {
	
	private int id;
	private String name;
	private String type;
	private double price;

	public ProductDto() {
		
	}
	

	public int getId() {
		return id;
	}


	public void setId(int i) {
		this.id = i;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
