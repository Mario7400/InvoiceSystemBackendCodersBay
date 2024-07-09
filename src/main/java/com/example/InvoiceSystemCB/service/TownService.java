package com.example.InvoiceSystemCB.service;


import com.example.InvoiceSystemCB.dto.TownDTO;
import com.example.InvoiceSystemCB.mapper.TownMapper;
import com.example.InvoiceSystemCB.model.Product;
import com.example.InvoiceSystemCB.model.Town;
import com.example.InvoiceSystemCB.repos.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TownService {

    @Autowired
    TownRepository townRepository;

    @Autowired
    TownMapper townMapper;

    public TownDTO createTown(TownDTO townDTO) {
        if (townRepository.existsByPlzAndTown(townDTO.getPlz(), townDTO.getTown())) {
            throw new IllegalArgumentException("This PLZ and town combination already exists!");
        }
        Town town = townMapper.toEntity(townDTO);
        townRepository.save(town);
        return townDTO;
    }


    public List<TownDTO> getAllTowns() {
        List<Town> allTowns = townRepository.findAll();
        List<TownDTO> newList = new ArrayList<>();
        for (Town t : allTowns){
            TownDTO townDTO = townMapper.toDTO(t);
            newList.add(townDTO);
        }
        return newList;
    }

}
