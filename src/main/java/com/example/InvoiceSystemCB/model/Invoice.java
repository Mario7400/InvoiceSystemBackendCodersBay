package com.example.InvoiceSystemCB.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String invoiceNumber;

    @ElementCollection
    @CollectionTable(name = "invoice_real_selling_price", joinColumns = @JoinColumn(name = "invoice_id"))
    @Column(name = "real_selling_price")
    private List<Double> realSellingPrices;

    @ElementCollection
    @CollectionTable(name = "invoice_amount_sold_product", joinColumns = @JoinColumn(name = "invoice_id"))
    @Column(name = "amount_of_sold_product", nullable = false, columnDefinition = "INT DEFAULT 0")
    private List<Integer> amountOfSoldProduct;

    private Date invoiceDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "invoice_product",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

}
