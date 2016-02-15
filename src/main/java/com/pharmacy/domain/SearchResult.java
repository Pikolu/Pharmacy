package com.pharmacy.domain;

import com.pharmacy.repository.utils.SortOrder;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Alexander on 07.02.2016.
 */
public class SearchResult {

    private static final int PAGE_COUNT = 10;
    private static final int SIZE_PER_PAGE = 20;

    private FacetedPage<Article> facetedPage;
    private List<String> pharmacies;
    private String parameter;
    private SortOrder sortOrder = SortOrder.RELEVANCE;
    private int firstPage = 1;
    private int currentPage = 1;
    private int lastPage = 10;

    public SearchResult() {

    }

    public SearchResult(FacetedPage<Article> facetedPage){
        this.facetedPage = facetedPage;
    }

    public FacetedPage<Article> getFacetedPage() {
        return facetedPage;
    }

    public void setFacetedPage(FacetedPage<Article> facetedPage) {
        this.facetedPage = facetedPage;
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

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public void buildPagination(int page, Model model, Long size) {
        int currentPage = page;
        int firstPage;
        int lastPage;
        int count;

        if (page == 0) {
            currentPage = 1;
            firstPage = 1;
            count = getPageCount(size);
            lastPage = getLastPage(count, PAGE_COUNT);
        } else {
            if (currentPage > 6) {
                firstPage = currentPage - 4;
                count = getPageCount(size);
                lastPage = getLastPage(count, currentPage + 5);
            } else {
                firstPage = 1;
                count = getPageCount(size);
                lastPage = getLastPage(count, PAGE_COUNT);
            }
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);
    }

    private int getLastPage(int count, int result) {
        if (count <= result) {
            return (count < 1) ? 1 : count;
        } else {
            return result;
        }
    }

    private int getPageCount(Long size) {
        double result = (int) Math.ceil(size * 1.0 / SIZE_PER_PAGE);
        return (int) result;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "facetedPage=" + facetedPage +
                ", pharmacies=" + pharmacies +
                ", parameter='" + parameter + '\'' +
                ", sortOrder=" + sortOrder +
                '}';
    }
}
