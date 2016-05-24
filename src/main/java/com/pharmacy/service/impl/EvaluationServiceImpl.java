package com.pharmacy.service.impl;

import com.pharmacy.domain.Evaluation;
import com.pharmacy.domain.Pharmacy;
import com.pharmacy.repository.EvaluationRepository;
import com.pharmacy.service.api.EvaluationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

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

    @Override
    public void evaluate(Evaluation evaluation, Pharmacy pharmacy) {
        Assert.notNull(evaluation);
        Assert.notNull(pharmacy);
        evaluation.setActive(false);
        evaluation.setPoints((float) (evaluation.getDescriptionPoints() + evaluation.getShippingPoints() + evaluation.getShippingPricePoints()) / 3);
        pharmacy.getEvaluations().add(evaluation);
        Float result = 0F;
        for (Evaluation ev : pharmacy.getEvaluations()) {
            result = result + ev.getPoints();
        }
        pharmacy.setTotalEvaluationPoints(Math.round(result / pharmacy.getEvaluations().size()));
        evaluation.setPharmacy(pharmacy);
    }

    @Override
    public List<Evaluation> getLastEvaluations(int size) {
        return evaluationRepository.getLastEvaluations(new PageRequest(0, size));
    }
}
