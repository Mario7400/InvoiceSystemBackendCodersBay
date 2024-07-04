package com.example.InvoiceSystemCB.mapper;

import com.example.InvoiceSystemCB.dto.InvoiceDTO;
import com.example.InvoiceSystemCB.model.Customer;
import com.example.InvoiceSystemCB.model.Invoice;
import com.example.InvoiceSystemCB.model.Product;
import com.example.InvoiceSystemCB.repos.CustomerRepository;
import com.example.InvoiceSystemCB.repos.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public Invoice toEntity(InvoiceDTO dto) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        invoice.setRealSellingPrices(dto.getRealSellingPrices());
        invoice.setAmountOfSoldProduct(dto.getAmountOfSoldProduct());

        if (dto.getInvoiceDate() != null) {
            invoice.setInvoiceDate(new Date(dto.getInvoiceDate().getTime()));
        }

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + dto.getCustomerId()));
        invoice.setCustomer(customer);

        List<Product> products = dto.getProductIds().stream()
                .map(productId -> productRepository.findById(productId).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        invoice.setProducts(products);

        return invoice;
    }


    public InvoiceDTO toDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setRealSellingPrices(invoice.getRealSellingPrices());
        dto.setAmountOfSoldProduct(invoice.getAmountOfSoldProduct());

        if (invoice.getInvoiceDate() != null) {
            dto.setInvoiceDate(new Date(invoice.getInvoiceDate().getTime()));
        }

        dto.setCustomerId(invoice.getCustomer().getId());

        List<Long> productIds = invoice.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        dto.setProductIds(productIds);

        return dto;
    }

}
