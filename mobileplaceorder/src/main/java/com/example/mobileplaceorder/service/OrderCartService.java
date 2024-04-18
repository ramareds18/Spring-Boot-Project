package com.example.mobileplaceorder.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobileplaceorder.entities.OrderItem;
import com.example.mobileplaceorder.repositories.OrderItemRepository;
import com.example.mobileplaceorder.service.dto.OrderItemDto;

@Service
public class OrderCartService {
	
    @Autowired
    private OrderItemRepository orderItemRepo;

    public List<OrderItemDto> getOrderCartItems() {
        return orderItemRepo.findAll().stream()
                             .filter(this::isItemNotOrdered)
                             .map(this::convertToDto)
                             .collect(Collectors.toList());
    }

    public OrderItemDto findById(int id) {
        Optional<OrderItemDto> optionalOrderItem = orderItemRepo.findById(id)
                                                                  .map(this::convertToDto);
        return optionalOrderItem.orElse(null);
    }
    
    public void addToCart(OrderItemDto orderItemDto) {
    	AtomicInteger foundItemId = new AtomicInteger(-1); // Initialize with a default value

    	List<OrderItemDto> existingItems = orderItemRepo.findAll().stream()
    	        .filter(item -> item.getName().equals(orderItemDto.getName()) &&
    	                item.getType().equals(orderItemDto.getType()) &&
    	                isItemNotOrdered(item))
    	        .peek(existingItem -> foundItemId.set(existingItem.getId())) // Save ID to the AtomicInteger
    	        .map(this::convertToDto)
    	        .collect(Collectors.toList());
        
        if (!existingItems.isEmpty()) {
            // If the item exists, update its quantity
            OrderItemDto existingItem = existingItems.get(0);
            existingItem.setQuantity(existingItem.getQuantity() + orderItemDto.getQuantity());
            OrderItem item = this.convertToEntity(existingItem);
            int itemId = foundItemId.get();
            item.setId(itemId);
            orderItemRepo.save(item);
        } else {
            // If the item doesn't exist, save it to the cart
            orderItemRepo.save(this.convertToEntity(orderItemDto));
        }
    }

    public void updateCartItem(OrderItemDto existingItem) {
        orderItemRepo.save(this.convertToEntity(existingItem));
    }

    public boolean emptyCart() {
    	int deletedCount = orderItemRepo.deleteByOrderNumberIsNull();
        return deletedCount > 0; // Return true if any items were deleted
    }
    
    // Helper methods for conversion between entities and DTOs
    private OrderItemDto convertToDto(OrderItem entity) {
        OrderItemDto dto = new OrderItemDto();
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        return dto;
    }

    private OrderItem convertToEntity(OrderItemDto dto) {
        OrderItem entity = new OrderItem();
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        return entity;
    }
    
    private boolean isItemNotOrdered(OrderItem orderItem) {
        return orderItem.getOrderNumber() == null;
    }
}
