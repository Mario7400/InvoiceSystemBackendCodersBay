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












//    @GetMapping("get/{cartId}")
//    public ResponseEntity<RedisCartDTO> getCart(@PathVariable String cartId) {
//        RedisCartDTO cartDTO = cartService.getCart(cartId);
//        return ResponseEntity.ok(cartDTO);
//    }
//
//    @PutMapping("update/{cartId}")
//    public ResponseEntity<Void> updateCart(@PathVariable String cartId, @RequestBody RedisCartDTO updatedCartDTO) {
//        cartService.updateCart(cartId, updatedCartDTO);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("exists/{cartId}")
//    public ResponseEntity<String> checkCartExists(@PathVariable String cartId) {
//        boolean exists = cartService.existByKey(cartId);
//        if (exists) {
//            return ResponseEntity.ok("Cart exists");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping("addItem/{cartId}")
//    public ResponseEntity<Void> addItemToCart(@PathVariable String cartId, @RequestParam Long productId, @RequestParam double price, @RequestParam int amount) {
//        cartService.addOrUpdateCartItem(cartId, productId, price, amount);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("removeItem/{cartId}")
//    public ResponseEntity<Void> removeItemFromCart(@PathVariable String cartId, @RequestParam Long productId) {
//        cartService.removeCartItem(cartId, productId);
//        return ResponseEntity.ok().build();
//    }




//    @PostMapping("post/{cartId}")
//    public ResponseEntity<Void> saveCart(@PathVariable String cartId, @RequestBody RedisCartDTO cartDTO) {
//        cartService.saveCart(cartId, cartDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @DeleteMapping("delete/{cartId}")
//    public ResponseEntity<Void> deleteCart(@PathVariable String cartId) {
//        cartService.deleteCart(cartId);
//        return ResponseEntity.ok().build();
//    }
//
//    @Autowired
//    private RedisCartService cartService;



//    @PostMapping("addItem/{cartId}")
//    public ResponseEntity<Void> saveCart(@PathVariable String cartId, @RequestBody RedisCartDTO cartDTO) {
//        cartService.saveCart(cartId, cartDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @GetMapping("get/{cartId}")
//    public ResponseEntity<RedisCartDTO> getCart(@PathVariable String cartId) {
//        RedisCartDTO cartDTO = cartService.getCart(cartId);
//        return ResponseEntity.ok(cartDTO);
//    }
//
//    @PutMapping("update/{cartId}")
//    public ResponseEntity<Void> updateCart(@PathVariable String cartId, @RequestBody RedisCartDTO updatedCartDTO) {
//        cartService.updateCart(cartId, updatedCartDTO);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("exists/{cartId}")
//    public ResponseEntity<String> checkCartExists(@PathVariable String cartId) {
//        boolean exists = cartService.existByKey(cartId);
//        if (exists) {
//            return ResponseEntity.ok("Cart exists");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("removeItem/{cartId}")
//    public ResponseEntity<Void> deleteCart(@PathVariable String cartId) {
//        cartService.deleteCart(cartId);
//        return ResponseEntity.ok().build();
//    }


