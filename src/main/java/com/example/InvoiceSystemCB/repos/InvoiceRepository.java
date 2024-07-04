package com.example.InvoiceSystemCB.repos;

import com.example.InvoiceSystemCB.model.Customer;
import com.example.InvoiceSystemCB.model.Invoice;
import com.example.InvoiceSystemCB.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findTopByInvoiceNumberStartingWithOrderByInvoiceNumberDesc(String invoiceNumberPrefix);

    List<Invoice> findByProducts(Product product);

    @Query(value = "SELECT * FROM invoice", nativeQuery = true)
    List<Invoice> findAllCompleteData();

    boolean existsByCustomer(Customer customer);
}
