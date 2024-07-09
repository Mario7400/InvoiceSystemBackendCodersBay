package com.example.InvoiceSystemCB.controller;


import com.example.InvoiceSystemCB.dto.ProductDTO;
import com.example.InvoiceSystemCB.model.Product;
import com.example.InvoiceSystemCB.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/post")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteProduct(@PathVariable String name) {
        try {
            productService.deleteProductByName(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<Product> updateProduct(@PathVariable String name, @RequestBody ProductDTO newProduct){
        Product product = productService.updateProduct(name, newProduct);
        if (product != null){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{productId}")
    public ProductDTO getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

}
