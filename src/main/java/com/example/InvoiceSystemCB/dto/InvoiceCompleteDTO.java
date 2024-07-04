package com.example.InvoiceSystemCB.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceCompleteDTO {
    private Long id;
    private String invoiceNumber;
    private List<Double> realSellingPrices;
    private List<Integer> amountOfSoldProduct;
    private Date invoiceDate;
    private CustomerDTO customer;

    private List<ProductDTO> products;
}
