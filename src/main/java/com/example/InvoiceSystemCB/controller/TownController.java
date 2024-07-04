package com.example.InvoiceSystemCB.controller;


import com.example.InvoiceSystemCB.dto.TownDTO;
import com.example.InvoiceSystemCB.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/towns")
public class TownController {

    @Autowired
    private TownService townService;

    @PostMapping("/post")
    public ResponseEntity<?> addTown(@RequestBody TownDTO townDTO) {
        try {
            TownDTO newTown = townService.createTown(townDTO);
            return ResponseEntity.ok(newTown);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TownDTO>> getAllTowns() {
        List<TownDTO> towns = townService.getAllTowns();
        return ResponseEntity.ok(towns);
    }

}
