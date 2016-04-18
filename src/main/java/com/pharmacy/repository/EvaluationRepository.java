package com.pharmacy.repository;

import com.pharmacy.domain.Evaluation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Pharmacy GmbH
 * Created by Alexander on 17.02.2016.
 */
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query(value = "SELECT e FROM Evaluation e ORDER BY e.creationDate DESC")
    List<Evaluation> getLastEvaluations(Pageable pageable);
}
