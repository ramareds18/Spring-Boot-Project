package com.example.mobileplaceorder.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.example.mobileplaceorder.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{
	
	Page<Product> findAll(Pageable pageable);
	List<Product> findByType(String type);
}
