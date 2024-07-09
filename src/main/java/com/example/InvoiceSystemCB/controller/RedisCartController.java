package com.example.InvoiceSystemCB.controller;


import com.example.InvoiceSystemCB.dto.RedisCartDTO;
import com.example.InvoiceSystemCB.service.RedisCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis/")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RedisCartController {

    @Autowired
    private RedisCartService redisCartService;

    @PostMapping("post/{cartId}")
    public ResponseEntity<Void> saveCart(@PathVariable String cartId, @RequestBody RedisCartDTO cartDTO) {
        redisCartService.saveCart(cartId, cartDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("get/{cartId}")
    public ResponseEntity<RedisCartDTO> getCart(@PathVariable String cartId) {
        RedisCartDTO cartDTO = redisCartService.getCart(cartId);
        if (cartDTO != null) {
            return ResponseEntity.ok(cartDTO);
        } else {
            return null;
        }
    }

    @GetMapping("exists")
    public boolean checkCartExists() {
        return redisCartService.existByKey(String.valueOf(1));
    }

}
