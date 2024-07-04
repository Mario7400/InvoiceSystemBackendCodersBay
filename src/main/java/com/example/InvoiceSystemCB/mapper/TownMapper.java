package com.example.InvoiceSystemCB.mapper;


import com.example.InvoiceSystemCB.dto.ProductDTO;
import com.example.InvoiceSystemCB.dto.TownDTO;
import com.example.InvoiceSystemCB.model.Product;
import com.example.InvoiceSystemCB.model.Town;
import org.springframework.stereotype.Component;

@Component
public class TownMapper {

    public TownDTO toDTO(Town town) {
        TownDTO dto = new TownDTO();
        dto.setId(town.getId());
        dto.setPlz(town.getPlz());
        dto.setTown(town.getTown());
        return dto;
    }

    public Town toEntity(TownDTO dto) {
        Town town = new Town();
        town.setId(dto.getId());
        town.setPlz(dto.getPlz());
        town.setTown(dto.getTown());
        return town;
    }

}
