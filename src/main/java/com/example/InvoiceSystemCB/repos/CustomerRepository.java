package com.example.InvoiceSystemCB.repos;

import com.example.InvoiceSystemCB.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByNameAndStreetAndStreetNrAndTownId(String name, String street, String streetNr, Long Id);

}
