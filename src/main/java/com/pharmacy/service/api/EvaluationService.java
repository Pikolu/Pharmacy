package com.pharmacy.service.api;

import com.pharmacy.domain.Evaluation;
import com.pharmacy.domain.Pharmacy;

import java.util.List;

/**
 * Pharmacy GmbH
 * Created by Alexander on 17.02.2016.
 */
public interface EvaluationService {

    Evaluation save(Evaluation evaluation);

    void evaluate(Evaluation evaluation, Pharmacy pharmacy);

    List<Evaluation> getLastEvaluations(int size);
}
