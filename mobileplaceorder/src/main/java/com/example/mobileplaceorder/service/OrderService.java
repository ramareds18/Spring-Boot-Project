package com.example.mobileplaceorder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobileplaceorder.entities.Order;
import com.example.mobileplaceorder.entities.OrderItem;
import com.example.mobileplaceorder.repositories.OrderItemRepository;
import com.example.mobileplaceorder.repositories.OrderRepository;
import com.example.mobileplaceorder.service.dto.OrderDto;
import com.example.mobileplaceorder.service.dto.OrderItemDto;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    public void placeOrder(OrderDto orderDto) {
        Order order = convertToEntity(orderDto);
        
        order.setOrderDate(LocalDateTime.now().toString());
        
        // Save the order to generate an order number
        orderRepository.save(order);
        
        // Get the generated order number
        int orderNumber = order.getOrderNumber();
        
        // Update order items with the order number
        List<OrderItemDto> orderItemsDto = orderDto.getOrderItems();
        for (OrderItemDto orderItemDto : orderItemsDto) {
            // Check if the order item already exists
            OrderItem existingItem = findExistingOrderItem(orderItemDto);
            
            if (existingItem != null) {
                existingItem.setOrderNumber(orderNumber);
                orderItemRepository.save(existingItem);
            } else {
                OrderItem orderItem = convertToEntity(orderItemDto);
                orderItem.setOrderNumber(orderNumber);
                orderItemRepository.save(orderItem);
            }
        }
    }
    
    private OrderItem findExistingOrderItem(OrderItemDto orderItemDto) {
        List<OrderItem> existingItems = orderItemRepository.findAll();
        
        // Iterate through the existing items and check if any match the provided details
        for (OrderItem item : existingItems) {
            if (item.getOrderNumber() == null &&
                item.getName().equals(orderItemDto.getName()) &&
                item.getType().equals(orderItemDto.getType()) &&
                item.getPrice() == orderItemDto.getPrice() &&
                item.getQuantity() == orderItemDto.getQuantity()) {
               return item;
            }
        }
        return null; 
    }
    
    private Order convertToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderDate(orderDto.getOrderDate());
        
        // Convert OrderItemDto objects to OrderItem entities
        List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                                            .map(this::convertToEntity)
                                            .collect(Collectors.toList());
        order.setOrderItems(orderItems);
        
        return order;
    }
    
    private OrderItem convertToEntity(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setName(orderItemDto.getName());
        orderItem.setType(orderItemDto.getType());
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setQuantity(orderItemDto.getQuantity());
        return orderItem;
    }
}
