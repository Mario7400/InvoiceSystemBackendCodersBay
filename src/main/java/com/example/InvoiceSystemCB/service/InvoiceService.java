package com.example.InvoiceSystemCB.service;


import com.example.InvoiceSystemCB.dto.InvoiceCompleteDTO;
import com.example.InvoiceSystemCB.dto.InvoiceDTO;
import com.example.InvoiceSystemCB.dto.ProductDTO;
import com.example.InvoiceSystemCB.invoiceCreator.InvoiceCreator;
import com.example.InvoiceSystemCB.mapper.CustomerMapper;
import com.example.InvoiceSystemCB.mapper.InvoiceMapper;
import com.example.InvoiceSystemCB.mapper.ProductMapper;
import com.example.InvoiceSystemCB.model.Invoice;
import com.example.InvoiceSystemCB.model.Product;
import com.example.InvoiceSystemCB.repos.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceCreator invoiceCreator;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceMapper invoiceMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ProductMapper productMapper;


    public void createInvoice(InvoiceDTO invoiceDto) {
        Invoice invoice = invoiceMapper.toEntity(invoiceDto);
        invoice.setInvoiceNumber(setInvoiceNumber());
        invoiceRepository.save(invoice);

        try {
            invoiceCreator.createPDF(invoice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String setInvoiceNumber(){
        String currentYear = String.valueOf(Year.now().getValue());
        String invoicePrefix = "R" + currentYear + "-";

        System.out.println("Invoice Prefix: " + invoicePrefix);

        Optional<Invoice> lastInvoiceOptional = invoiceRepository
                .findTopByInvoiceNumberStartingWithOrderByInvoiceNumberDesc(invoicePrefix);

        if (lastInvoiceOptional.isPresent()) {
            Invoice lastInvoice = lastInvoiceOptional.get();
            String lastInvoiceNumber = lastInvoice.getInvoiceNumber();
            int lastNumber = Integer.parseInt(lastInvoiceNumber.substring(invoicePrefix.length()));
            return invoicePrefix + String.format("%04d", lastNumber + 1);
        } else {
            return invoicePrefix + String.format("%04d", 1);
        }
    }

    public List<InvoiceDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAllCompleteData();
        return invoices.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<InvoiceCompleteDTO> getAllInvoicesWithDetails() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .map(this::mapToInvoiceFullDTO)
                .collect(Collectors.toList());
    }

    public InvoiceCompleteDTO getInvoiceDetailsById(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found with ID: " + invoiceId));
        return mapToInvoiceFullDTO(invoice);
    }

    private InvoiceCompleteDTO mapToInvoiceFullDTO(Invoice invoice) {
        InvoiceCompleteDTO dto = new InvoiceCompleteDTO();
        dto.setId(invoice.getId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setRealSellingPrices(invoice.getRealSellingPrices());
        dto.setAmountOfSoldProduct(invoice.getAmountOfSoldProduct());
        dto.setInvoiceDate(new Date(invoice.getInvoiceDate().getTime()));
        dto.setCustomer(customerMapper.toDTO(invoice.getCustomer()));
        dto.setProducts(mapToProductDTOList(invoice.getProducts()));
        return dto;
    }

    private List<ProductDTO> mapToProductDTOList(List<Product> products) {
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

}
