package com.example.InvoiceSystemCB.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisCartDTO {

    private List<Double> realSellingPrices;
    private List<Integer> amountOfSoldProduct;
    private Date invoiceDate;
    private Long customerId;
    private List<Long> productIds;
}
