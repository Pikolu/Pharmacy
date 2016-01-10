package com.pharmacy.repository;

import com.pharmacy.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Alexander on 03.01.2016.
 */
public interface PriceRepository extends JpaRepository<Price, Long> {

}
