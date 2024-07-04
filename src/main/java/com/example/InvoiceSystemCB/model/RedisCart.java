package com.example.InvoiceSystemCB.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RedisCart {

    private List<Double> realSellingPrices;
    private List<Integer> amountOfSoldProduct;
    private Date invoiceDate;
    private Long customerId;
    private List<Long> productIds;

}
