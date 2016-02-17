package com.pharmacy.repository;

import com.pharmacy.domain.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Pharmacy GmbH
 * Created by Alexander on 17.02.2016.
 */
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
