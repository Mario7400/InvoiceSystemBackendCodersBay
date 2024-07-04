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
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String plz;
    private String town;


    @OneToMany(mappedBy = "town", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("town")
    private List<Customer> customers;
}
