package com.pharmacy.repository.utils;

import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alexander on 01.02.2016.
 */
public class FilterOptions {

    private List<String> pharmacies;

    private SortOrder sortOrder = SortOrder.RELEVANCE;

    public void addPharmacies(TermResult termResult) {
        this.pharmacies = termResult.getTerms().stream().map(Term::getTerm).collect(Collectors.toList());
    }

    public List<String> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(List<String> pharmacies) {
        this.pharmacies = pharmacies;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
