package com.example.InvoiceSystemCB.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter und setter + ToString und noch etwas
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long id;
    private String name;
    private String secondName;
    private String street;
    private String streetNr;
    private String phoneNumber;
    private String email;
    private String companyName;
    private Long townId;
    private String plz;
    private String town;

}
