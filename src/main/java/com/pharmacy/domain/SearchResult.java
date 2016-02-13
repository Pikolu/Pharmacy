package com.pharmacy.domain;

import com.pharmacy.repository.utils.SortOrder;
import org.springframework.data.elasticsearch.core.FacetedPage;

import java.util.List;

/**
 * Created by Alexander on 07.02.2016.
 */
public class SearchResult {

    private FacetedPage<Article> page;
    private List<String> pharmacies;
    private String parameter;
    private SortOrder sortOrder = SortOrder.RELEVANCE;

    public SearchResult() {

    }

    public SearchResult(FacetedPage<Article> page){
        this.page = page;
    }

    public FacetedPage<Article> getPage() {
        return page;
    }

    public void setPage(FacetedPage<Article> page) {
        this.page = page;
    }

    public List<String> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(List<String> pharmacies) {
        this.pharmacies = pharmacies;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "page=" + page +
                ", pharmacies=" + pharmacies +
                ", parameter='" + parameter + '\'' +
                ", sortOrder=" + sortOrder +
                '}';
    }
}
