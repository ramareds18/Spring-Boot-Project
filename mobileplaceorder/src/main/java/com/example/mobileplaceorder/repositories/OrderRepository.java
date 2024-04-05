package com.example.mobileplaceorder.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.mobileplaceorder.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{

}
