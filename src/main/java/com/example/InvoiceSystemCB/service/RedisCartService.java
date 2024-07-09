package com.example.InvoiceSystemCB.service;


import com.example.InvoiceSystemCB.dto.RedisCartDTO;
import com.example.InvoiceSystemCB.mapper.RedisCartMapper;
import com.example.InvoiceSystemCB.model.RedisCart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RedisCartService {

    private static final String CART_KEY_PREFIX = "cart:";
    private static final long TTL_MINUTES = 1;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper; // Jackson ObjectMapper for JSON serialization

    @Autowired
    private RedisCartMapper redisCartMapper;

    public void saveCart(String cartId, RedisCartDTO cartDTO) {
        String key = CART_KEY_PREFIX + cartId;
        try {
            String jsonValue = objectMapper.writeValueAsString(redisCartMapper.mapToEntity(cartDTO));
            redisTemplate.opsForValue().set(key, jsonValue, TTL_MINUTES, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            Logger.getLogger(RedisCartService.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public RedisCartDTO getCart(String cartId) {
        String key = CART_KEY_PREFIX + cartId;
        String jsonValue = redisTemplate.opsForValue().get(key);
        if (jsonValue != null) {
            try {
                return redisCartMapper.mapToDto(objectMapper.readValue(jsonValue, RedisCart.class));
            } catch (JsonProcessingException e) {
                Logger.getLogger(RedisCartService.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }


    public boolean existByKey(String cartId) {
        String key = CART_KEY_PREFIX + cartId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
