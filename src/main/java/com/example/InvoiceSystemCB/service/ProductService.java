package com.example.InvoiceSystemCB.service;

import com.example.InvoiceSystemCB.dto.ProductDTO;
import com.example.InvoiceSystemCB.mapper.ProductMapper;
import com.example.InvoiceSystemCB.model.Invoice;
import com.example.InvoiceSystemCB.model.Product;
import com.example.InvoiceSystemCB.repos.InvoiceRepository;
import com.example.InvoiceSystemCB.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductMapper productMapper;


    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }


    public List<ProductDTO> getAllProducts(){
        List<Product> allProducts = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product allProduct : allProducts) {
            ProductDTO productDTO = productMapper.toDTO(allProduct);
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }


//    public void deleteProductByName(String name){
//        List<Product> byName = productRepository.findByName(name);
//        if (!byName.isEmpty()){
//            productRepository.deleteAll(byName);
//        }
//    }


    public void deleteProductByName(String name) {
        List<Product> products = productRepository.findByName(name);
        if (!products.isEmpty()) {
            for (Product product : products) {
                if (isProductInUse(product)) {
                    throw new IllegalStateException("Product is in use and cannot be deleted.");
                }
            }
            productRepository.deleteAll(products);
        }
    }

    private boolean isProductInUse(Product product) {
        List<Invoice> invoicesWithProduct = invoiceRepository.findByProducts(product);
        return !invoicesWithProduct.isEmpty();
    }





    public Product updateProduct(String name, ProductDTO newProduct){
        List<Product> byName = productRepository.findByName(name);
        if(!byName.isEmpty()){
            Product currentProduct = byName.get(0);
            currentProduct.setName(newProduct.getName());
            currentProduct.setDescription(newProduct.getDescription());
            currentProduct.setPrice(newProduct.getPrice());
            currentProduct.setAvailability(newProduct.isAvailability());
            return productRepository.save(currentProduct);
        }
        return null;
    }

//    public Movie updateNewMovie(Long id, Movie newMovie){
//        Optional<Movie> byId = movieRepository.findById(id);
//
//        if (byId.isPresent()){
//            Movie currentMovie = byId.get();
//            currentMovie.setTitle(newMovie.getTitle());
//            currentMovie.setAgeRating(newMovie.getAgeRating());
//            return movieRepository.save(currentMovie);
//        }
//        return null;
//    }
//










//
//
//    public void deleteMovieByTitle(String title){
//        List<Movie> byTitle = findByTitle(title);
//        if(!byTitle.isEmpty()){
//            movieRepository.deleteAll(byTitle);
//        }
//    }
//
//
//    public void deleteProductByName(String name){
//        productRepository.deleteByName(name);
////        List<Product> byName = productRepository.findByName(name);
////        if (!byName.isEmpty()){
////            productRepository.deleteByName(name);
////        }
//    }
//





//    public void deleteMovieByTitle(String title){
//        List<Movie> byTitle = findByTitle(title);
//        if(!byTitle.isEmpty()){
//            movieRepository.deleteAll(byTitle);
//        }
//    }



//    public List<ProductDTO> getAllProducts() {
//        return productRepository.findAll().stream()
//                .map(productMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//
//
//    public void deleteProduct(Long id) {
//        productRepository.deleteById(id);
//    }
//
//    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
//        return productRepository.findById(id).map(existingProduct -> {
//            existingProduct.setName(productDTO.getName());
//            existingProduct.setDescription(productDTO.getDescription());
//            existingProduct.setPrice(productDTO.getPrice());
//            existingProduct.setAvailability(productDTO.isAvailability());
//            Product updatedProduct = productRepository.save(existingProduct);
//            return productMapper.toDTO(updatedProduct);
//        }).orElse(null);
//    }
//
//    public ProductDTO getProductById(Long id) {
//        return productRepository.findById(id)
//                .map(productMapper::toDTO)
//                .orElse(null);
//    }


}
