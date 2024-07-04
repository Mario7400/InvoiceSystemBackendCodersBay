package com.example.InvoiceSystemCB.mapper;

import com.example.InvoiceSystemCB.dto.CustomerDTO;
import com.example.InvoiceSystemCB.model.Customer;
import com.example.InvoiceSystemCB.model.Town;
import com.example.InvoiceSystemCB.repos.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    @Autowired
    private TownRepository townRepository;

    public CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setSecondName(customer.getSecondName());
        dto.setStreet(customer.getStreet());
        dto.setStreetNr(customer.getStreetNr());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setEmail(customer.getEmail());
        dto.setCompanyName(customer.getCompanyName());
        if (customer.getTown() != null) {
            dto.setTownId(customer.getTown().getId());
            dto.setPlz(customer.getTown().getPlz());
            dto.setTown(customer.getTown().getTown());
        }
        return dto;
    }

    public Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setSecondName(dto.getSecondName());
        customer.setStreet(dto.getStreet());
        customer.setStreetNr(dto.getStreetNr());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setEmail(dto.getEmail());
        customer.setCompanyName(dto.getCompanyName());
        if (dto.getTownId() != null) {
            Town town = townRepository.findById(dto.getTownId()).orElse(null);
            customer.setTown(town);
        }
        return customer;
    }

}
