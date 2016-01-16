package com.pharmacy.service.api;

import com.pharmacy.domain.Evaluation;
import com.pharmacy.domain.Pharmacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Alexander on 09.01.2016.
 */
public interface PharmacyService {

    Page<Pharmacy> getPharmacyByName(String name, Pageable pageable);

    List<Pharmacy> findBestPharmacies();

    List<Pharmacy> findPharmaciesByName(String pharmacyName);

    void saveEvaluation(String pharmId, Evaluation evaluation);

    Pharmacy getPharmacyById(String pharmId);

    void save(Pharmacy pharmacy);
}
