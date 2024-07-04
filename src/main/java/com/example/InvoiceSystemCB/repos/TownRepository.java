package com.example.InvoiceSystemCB.repos;

import com.example.InvoiceSystemCB.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    boolean existsByPlzAndTown(String plz, String town);
}
