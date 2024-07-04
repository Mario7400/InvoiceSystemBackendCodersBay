package com.example.InvoiceSystemCB.controller;


import com.example.InvoiceSystemCB.dto.InvoiceDTO;
import com.example.InvoiceSystemCB.dto.InvoiceCompleteDTO;
import com.example.InvoiceSystemCB.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/post")
    public ResponseEntity<String> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        try {
            invoiceService.createInvoice(invoiceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Invoice created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create invoice");
        }
    }

    @GetMapping("/all")
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/complete")
    public ResponseEntity<List<InvoiceCompleteDTO>> getAllCompleteInvoices() {
        List<InvoiceCompleteDTO> invoices = invoiceService.getAllInvoicesWithDetails();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("complete/{invoiceId}")
    public ResponseEntity<InvoiceCompleteDTO> getCompleteInvoiceById(@PathVariable Long invoiceId) {
        InvoiceCompleteDTO invoice = invoiceService.getInvoiceDetailsById(invoiceId);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }

}
