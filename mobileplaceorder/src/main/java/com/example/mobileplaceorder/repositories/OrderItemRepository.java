package com.example.mobileplaceorder.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.mobileplaceorder.entities.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer>{
	List<OrderItem> findAll();
}
