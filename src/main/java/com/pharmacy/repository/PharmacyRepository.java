package com.pharmacy.repository;

import com.pharmacy.domain.Pharmacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Alexander on 14.11.2015.
 */
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    @Query(value = "SELECT a FROM Pharmacy a where a.name like %?1")
    Page<Pharmacy> findPharmacyByName(String name, Pageable pageable);

    @Query(value = "SELECT p FROM Pharmacy p LEFT JOIN p.evaluations e WHERE e.points > 0 GROUP BY p.id")
    List<Pharmacy> findBestPharmacies();
}
