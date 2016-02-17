package com.pharmacy.service.impl;

import com.pharmacy.domain.Evaluation;
import com.pharmacy.repository.EvaluationRepository;
import com.pharmacy.service.api.EvaluationService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Pharmacy GmbH
 * Created by Alexander on 17.02.2016.
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Inject
    private EvaluationRepository evaluationRepository;

    @Override
    public Evaluation save(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }
}
