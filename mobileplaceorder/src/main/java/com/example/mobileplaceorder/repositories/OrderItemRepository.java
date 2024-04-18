package com.example.mobileplaceorder.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.mobileplaceorder.entities.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer>{
	List<OrderItem> findAll();
	
	@Transactional
	@Modifying
    @Query(nativeQuery = true, value = "DELETE FROM order_item_tbl WHERE ordernumber IS NULL")
    int deleteByOrderNumberIsNull();
}
