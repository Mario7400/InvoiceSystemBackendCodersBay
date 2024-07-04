package com.example.InvoiceSystemCB.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TownDTO {

    private Long id;
    private String plz;
    private String town;

}
