package com.example.InvoiceSystemCB.service;


import com.example.InvoiceSystemCB.dto.CustomerDTO;
import com.example.InvoiceSystemCB.dto.ProductDTO;
import com.example.InvoiceSystemCB.mapper.CustomerMapper;
import com.example.InvoiceSystemCB.model.Customer;
import com.example.InvoiceSystemCB.model.Product;
import com.example.InvoiceSystemCB.model.Town;
import com.example.InvoiceSystemCB.repos.CustomerRepository;
import com.example.InvoiceSystemCB.repos.InvoiceRepository;
import com.example.InvoiceSystemCB.repos.TownRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TownRepository townRepository;

    public CustomerDTO createCustomer(CustomerDTO customerDTO){
        Optional<Customer> existingCustomer = customerRepository.findByNameAndStreetAndStreetNrAndTownId(customerDTO.getName(), customerDTO.getStreet(),customerDTO.getStreetNr(), customerDTO.getTownId());

        if (existingCustomer.isPresent()){
            throw new IllegalArgumentException("This customer already exists!");
        } else {
            Customer customer = customerMapper.toEntity(customerDTO);
            customerRepository.save(customer);
            return customerDTO;
        }
    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> allCustomers = customerRepository.findAll();
        List<CustomerDTO> newList = new ArrayList<>();
        for (Customer c : allCustomers){
            CustomerDTO customerDTO = customerMapper.toDTO(c);
            newList.add(customerDTO);
        }
        return newList;
    }

    public void deleteCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        if (!invoiceRepository.existsByCustomer(customer)) {
            // If no invoices are associated with this customer, delete the customer
            customerRepository.delete(customer);
        } else {
            throw new IllegalStateException("Customer is associated with invoices and cannot be deleted.");
        }
    }

    public Customer updateCustomer(Long id, CustomerDTO newCustomer) {
        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isPresent() && newCustomer != null) {
            Customer currentCustomer = byId.get();
            currentCustomer.setName(newCustomer.getName());
            currentCustomer.setSecondName(newCustomer.getSecondName());
            currentCustomer.setStreet(newCustomer.getStreet());
            currentCustomer.setStreetNr(newCustomer.getStreetNr());
            currentCustomer.setPhoneNumber(newCustomer.getPhoneNumber());
            currentCustomer.setEmail(newCustomer.getEmail());
            currentCustomer.setCompanyName(newCustomer.getCompanyName());
            // Überprüfe, ob die Town ID gültig ist
            if (newCustomer.getTownId() != null) {
                // Setze die Stadt des aktuellen Kunden auf die Town ID aus dem neuen Kundenobjekt
                Optional<Town> townOptional = townRepository.findById(newCustomer.getTownId());
                if (townOptional.isPresent()) {
                    // Setze die Stadt des aktuellen Kunden auf die abgerufene Stadt
                    currentCustomer.setTown(townOptional.get());
                } else {
                    return null;
                }
            }
            return customerRepository.save(currentCustomer);
        }
        return null;
    }

}
