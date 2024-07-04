package com.example.InvoiceSystemCB.mapper;


import com.example.InvoiceSystemCB.dto.RedisCartDTO;
import com.example.InvoiceSystemCB.model.RedisCart;
import org.springframework.stereotype.Component;

@Component
public class RedisCartMapper {
    public RedisCartDTO mapToDto(RedisCart redisCart) {
        RedisCartDTO dto = new RedisCartDTO();
        dto.setRealSellingPrices(redisCart.getRealSellingPrices());
        dto.setAmountOfSoldProduct(redisCart.getAmountOfSoldProduct());
        dto.setInvoiceDate(redisCart.getInvoiceDate());
        dto.setCustomerId(redisCart.getCustomerId());
        dto.setProductIds(redisCart.getProductIds());
        return dto;
    }

    public RedisCart mapToEntity(RedisCartDTO dto) {
        RedisCart redisCart = new RedisCart();
        redisCart.setRealSellingPrices(dto.getRealSellingPrices());
        redisCart.setAmountOfSoldProduct(dto.getAmountOfSoldProduct());
        redisCart.setInvoiceDate(dto.getInvoiceDate());
        redisCart.setCustomerId(dto.getCustomerId());
        redisCart.setProductIds(dto.getProductIds());
        return redisCart;
    }
}
