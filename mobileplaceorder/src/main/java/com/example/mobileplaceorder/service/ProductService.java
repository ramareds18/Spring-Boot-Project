package com.example.mobileplaceorder.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.mobileplaceorder.entities.Product;
import com.example.mobileplaceorder.repositories.ProductRepository;
import com.example.mobileplaceorder.service.dto.ProductDto;


@Service
@Transactional
public class ProductService {
	
    @Autowired
    private ProductRepository productRepo;

    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> productsPage = productRepo.findAll(pageable);
        return productsPage.map(this::convertToDto);
    }

    public ProductDto findById(int id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        return optionalProduct.map(this::convertToDto).orElse(null);
    }
    
    public List<ProductDto> findByType(String type) {
        List<Product> products = productRepo.findByType(type);
        return products.stream()
                       .map(this::convertToDto)
                       .collect(Collectors.toList());
    }

    public ProductDto save(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        Product savedProduct = productRepo.save(product);
        return convertToDto(savedProduct);
    }
    
    public ProductDto updateProduct(int id, ProductDto updatedProductDto) {
        ProductDto existingProductDto = findById(id);
        if (existingProductDto != null) {
            existingProductDto.setName(updatedProductDto.getName());
            existingProductDto.setType(updatedProductDto.getType());
            existingProductDto.setPrice(updatedProductDto.getPrice());
            
            // Convert the updated product DTO back to entity
            Product updatedProductEntity = convertToEntity(existingProductDto);
            
            // Set the ID of the updated entity
            updatedProductEntity.setId(id);
            
            // Save the updated product entity to the database
            Product savedProductEntity = productRepo.save(updatedProductEntity);
            
            // Convert the saved product entity back to DTO and return it
            return convertToDto(savedProductEntity);
        } else {
            return null;
        }
    }

    public void delete(int id) {
        productRepo.deleteById(id);
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setType(product.getType());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setType(productDto.getType());
        product.setPrice(productDto.getPrice());
        return product;
    }
}
