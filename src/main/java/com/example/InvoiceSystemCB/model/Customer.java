package com.example.InvoiceSystemCB.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String secondName;
    private String street;
    private String streetNr;
    private String phoneNumber;
    private String email;
    private String companyName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "town_id")
    @JsonIgnoreProperties("customers")
    private Town town;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices;

}
