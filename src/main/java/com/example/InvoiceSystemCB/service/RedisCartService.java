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
            // Log the exception and handle it properly
            Logger.getLogger(RedisCartService.class.getName()).log(Level.SEVERE, null, e);
            // Optionally rethrow the exception or handle it in another way
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
//                e.printStackTrace();
//                // Handle JSON processing exception
//                return null;
            }
        }
        return null;
    }


    public boolean existByKey(String cartId) {
        String key = CART_KEY_PREFIX + cartId;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

//    public void updateCart(String cartId, RedisCartDTO updatedCartDTO) {
//        if (updatedCartDTO == null || cartId == null || cartId.isEmpty()) {
//            throw new IllegalArgumentException("Invalid cart ID or cart data");
//        }
//        saveCart(cartId, updatedCartDTO);
//    }
//

//
//    public void removeCartItem(String cartId) {
//        String key = CART_KEY_PREFIX + cartId;
//        redisTemplate.delete(key);
//    }







}


















//    public void saveCart(String cartId, RedisCartDTO cartDTO) {
//        String key = CART_KEY_PREFIX + cartId;
//        try {
//            String jsonValue = objectMapper.writeValueAsString(redisCartMapper.mapToEntity(cartDTO));
//            redisTemplate.opsForValue().set(key, jsonValue, TTL_MINUTES, TimeUnit.MINUTES);
//        } catch (JsonProcessingException e) {
//            Logger.getLogger(RedisCartService.class.getName()).log(Level.SEVERE, null, e);
//        }
//    }
//
//    public RedisCartDTO getCart(String cartId) {
//        String key = CART_KEY_PREFIX + cartId;
//        String jsonValue = redisTemplate.opsForValue().get(key);
//        if (jsonValue != null) {
//            try {
//                return redisCartMapper.mapToDto(objectMapper.readValue(jsonValue, RedisCart.class));
//            } catch (JsonProcessingException e) {
//                Logger.getLogger(RedisCartService.class.getName()).log(Level.SEVERE, null, e);
//            }
//        }
//        return null;
//    }
//
//    public void updateCart(String cartId, RedisCartDTO updatedCartDTO) {
//        saveCart(cartId, updatedCartDTO);
//    }
//
//    public boolean existByKey(String cartId) {
//        String key = CART_KEY_PREFIX + cartId;
//        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
//    }
//
//    public void deleteCart(String cartId) {
//        String key = CART_KEY_PREFIX + cartId;
//        redisTemplate.delete(key);
//    }
//
//    public void addOrUpdateCartItem(String cartId, Long productId, double price, int amount) {
//        RedisCartDTO cart = getCart(cartId);
//        if (cart == null) {
//            cart = new RedisCartDTO();
//            cart.setCustomerId(Long.parseLong(cartId)); // Assuming cartId is the same as customerId
//            cart.setProductIds(new ArrayList<>());
//            cart.setRealSellingPrices(new ArrayList<>());
//            cart.setAmountOfSoldProduct(new ArrayList<>());
//        }
//
//        List<Long> productIds = cart.getProductIds();
//        List<Double> prices = cart.getRealSellingPrices();
//        List<Integer> amounts = cart.getAmountOfSoldProduct();
//
//        int index = productIds.indexOf(productId);
//        if (index >= 0) {
//            amounts.set(index, amounts.get(index) + amount);
//        } else {
//            productIds.add(productId);
//            prices.add(price);
//            amounts.add(amount);
//        }
//        saveCart(cartId, cart);
//    }
//
//    public void removeCartItem(String cartId, Long productId) {
//        RedisCartDTO cart = getCart(cartId);
//        if (cart != null) {
//            List<Long> productIds = cart.getProductIds();
//            List<Double> prices = cart.getRealSellingPrices();
//            List<Integer> amounts = cart.getAmountOfSoldProduct();
//
//            int index = productIds.indexOf(productId);
//            if (index >= 0) {
//                productIds.remove(index);
//                prices.remove(index);
//                amounts.remove(index);
//                saveCart(cartId, cart);
//            }
//        }
//    }
















