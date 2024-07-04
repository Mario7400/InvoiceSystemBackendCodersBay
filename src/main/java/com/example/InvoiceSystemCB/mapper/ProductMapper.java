package com.example.InvoiceSystemCB.mapper;


import com.example.InvoiceSystemCB.dto.ProductDTO;
import com.example.InvoiceSystemCB.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setId(product.getId());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setAvailability(product.isAvailability());
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setId(dto.getId());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setAvailability(dto.isAvailability());
        return product;
    }

}
