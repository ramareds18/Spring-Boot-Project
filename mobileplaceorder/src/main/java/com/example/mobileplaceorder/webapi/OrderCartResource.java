package com.example.mobileplaceorder.webapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobileplaceorder.service.OrderCartService;
import com.example.mobileplaceorder.service.ProductService;
import com.example.mobileplaceorder.service.dto.OrderItemDto;
import com.example.mobileplaceorder.service.dto.ProductDto;

@RestController
public class OrderCartResource {
    
    @Autowired
    private OrderCartService orderCartService;
    
    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public ResponseEntity<?> getOrderCart() {
    	List<OrderItemDto> cartItems = orderCartService.getOrderCartItems();
        if (!cartItems.isEmpty()) {
            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart is empty");
        }
    }
    
    @PostMapping("/products/cart/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable int productId, @RequestParam int qty) {
    	
        if (qty <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid quantity: Quantity must be a positive integer");
        }
        
        ProductDto productDto = productService.findById(productId);
        if (productDto != null) {
            // Create a new order item
            OrderItemDto orderItem = new OrderItemDto();
            orderItem.setName(productDto.getName());
            orderItem.setType(productDto.getType());
            orderItem.setPrice(productDto.getPrice());
            orderItem.setQuantity(qty);
            
            // Add the order item to the cart
            orderCartService.addToCart(orderItem);
            
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added to cart successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
    
    @DeleteMapping("/cart")
    public ResponseEntity<String> emptyCart() {
        boolean itemsDeleted = orderCartService.emptyCart();
        
        if (itemsDeleted) {
            return ResponseEntity.ok("Cart emptied successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No items to delete");
        }
    }
}
