package com.example.mobileplaceorder.webapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobileplaceorder.service.OrderService;
import com.example.mobileplaceorder.service.dto.OrderDto;

@RestController
public class OrderResource {

    @Autowired
    private OrderService orderService;

    
    @PostMapping("/cart")
    public ResponseEntity<String> placeOrder(@RequestBody OrderDto orderDto) {
        orderService.placeOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order placed successfully");
    }
	
}
