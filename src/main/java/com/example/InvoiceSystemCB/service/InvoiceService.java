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

        // Find the highest invoice number for the current year
        Optional<Invoice> lastInvoiceOptional = invoiceRepository
                .findTopByInvoiceNumberStartingWithOrderByInvoiceNumberDesc(invoicePrefix);

        if (lastInvoiceOptional.isPresent()) {
            Invoice lastInvoice = lastInvoiceOptional.get();
            String lastInvoiceNumber = lastInvoice.getInvoiceNumber();
            int lastNumber = Integer.parseInt(lastInvoiceNumber.substring(invoicePrefix.length()));
            return invoicePrefix + String.format("%04d", lastNumber + 1);
        } else {
            // No invoice for the current year
            return invoicePrefix + String.format("%04d", 1);
        }
    }

    public List<InvoiceDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAllCompleteData();
        return invoices.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }


    // Methods for my complete Responses:
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










//    public List<InvoiceFullDTO> getAllCompleteInvoices() {
//        List<Object[]> results = invoiceRepository.findAllCompleteData();
//        Map<Long, InvoiceFullDTO> invoiceMap = new HashMap<>();
//
//        for (Object[] result : results) {
//            Long invoiceId = ((Number) result[0]).longValue();
//            Date invoiceDate = (Date) result[1];
//
//            Long customerId = ((Number) result[2]).longValue();
//            String customerName = (String) result[3];
//            String customerSecondName = (String) result[4];
//            String customerStreet = (String) result[5];
//            String customerStreetNr = (String) result[6];
//            String customerPhoneNumber = (String) result[7];
//            String customerEmail = (String) result[8];
//            String customerCompanyName = (String) result[9];
//            boolean customerBoughtSomething = (boolean) result[10];
//            Long townId = ((Number) result[11]).longValue();
//            String plz = (String) result[12];
//            String town = (String) result[13];
//
//            CustomerDTO customerDTO = new CustomerDTO(customerId, customerName, customerSecondName, customerStreet, customerStreetNr, customerPhoneNumber, customerEmail, customerCompanyName, customerBoughtSomething, townId, plz, town);
//
//            Long productId = ((Number) result[14]).longValue();
//            String productName = (String) result[15];
//            String productDescription = (String) result[16];
//            double productPrice = ((Number) result[17]).doubleValue();
//            boolean productAvailability = (boolean) result[18];
//            boolean productStatusInUse = (boolean) result[19];
//
//            ProductDTO productDTO = new ProductDTO(productId, productName, productDescription, productPrice, productAvailability, productStatusInUse);
//
//            Double realSellingPrice = (Double) result[20];
//            Integer amountOfSoldProduct = (Integer) result[21];
//
//            if (!invoiceMap.containsKey(invoiceId)) {
//                List<Double> realSellingPrices = new ArrayList<>();
//                realSellingPrices.add(realSellingPrice);
//                List<Integer> amountOfSoldProducts = new ArrayList<>();
//                amountOfSoldProducts.add(amountOfSoldProduct);
//                List<ProductDTO> products = new ArrayList<>();
//                products.add(productDTO);
//
//                InvoiceFullDTO invoiceDTO = new InvoiceFullDTO(invoiceId, realSellingPrices, amountOfSoldProducts, invoiceDate, customerDTO, products);
//                invoiceMap.put(invoiceId, invoiceDTO);
//            } else {
//                InvoiceFullDTO existingInvoiceDTO = invoiceMap.get(invoiceId);
//                existingInvoiceDTO.getRealSellingPrices().add(realSellingPrice);
//                existingInvoiceDTO.getAmountOfSoldProduct().add(amountOfSoldProduct);
//                existingInvoiceDTO.getProducts().add(productDTO);
//            }
//        }
//
//        return new ArrayList<>(invoiceMap.values());
//    }
}


//    public List<InvoiceDTO> getAllInvoices() {
//        List<Invoice> invoices = invoiceRepository.findAll();
//        List<InvoiceDTO> invoiceDTOList = invoiceMapper.toDTO(invoices);
//        return invoiceDTOList;
//    }

//    @Transactional
//    public void createInvoice(InvoiceDTO invoiceDTO) {
//        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
//
//        // Setze die Produkte der Rechnung
//        List<Product> products = invoiceDTO.getProductIds().stream()
//                .map(productId -> {
//                    Product product = new Product();
//                    product.setId(productId);
//                    return product;
//                })
//                .collect(Collectors.toList());
//        invoice.setProducts(products);
//
//        invoiceRepository.save(invoice);
//    }


//    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
//        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
//        invoice = invoiceRepository.save(invoice);
//        return invoiceMapper.toDTO(invoice);
//    }
//
//    public List<InvoiceDTO> getAllInvoices() {
//        List<Invoice> invoices = invoiceRepository.findAll();
//        return invoices.stream()
//                .map(invoiceMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    public InvoiceDTO getInvoiceById(Long id) {
//        Invoice invoice = invoiceRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Invoice not found"));
//        return invoiceMapper.toDTO(invoice);
//    }
//
//    public void deleteInvoiceById(Long id) {
//        invoiceRepository.deleteById(id);
//    }

//    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {
//        Invoice invoice = InvoiceMapper.toEntity(invoiceDTO);
//        invoice = invoiceRepository.save(invoice);
//        return invoiceMapper.toDTO(invoice);
//    }
//
//    public List<InvoiceDTO> getAllInvoices(){
//        List<Invoice> allInvoices = invoiceRepository.findAll();
//        List<InvoiceDTO> newList = new ArrayList<>();
//        for (Invoice i : allInvoices){
//            InvoiceDTO invoiceDTO = invoiceMapper.toDTO(i);
//            newList.add(invoiceDTO);
//        }
//        return newList;
//    }
//
//    public InvoiceDTO getInvoiceById(Long id){
//        Optional<Invoice> byId = invoiceRepository.findById(id);
//        if (byId.isPresent()) {
//            return invoiceMapper.toDTO(byId.get());
//        } else {
//            return null;
//        }
//    }
//
//    public void deleteById(Long id) {
//        invoiceRepository.deleteById(id);
//    }


