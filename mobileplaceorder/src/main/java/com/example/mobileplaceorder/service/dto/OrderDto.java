package com.example.mobileplaceorder.service.dto;

import java.util.List;

public class OrderDto {
	
	private String orderDate;
	
    private List<OrderItemDto> orderItems;

	public OrderDto() {
		
	}
    
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}    
	
}
