package com.pharmacy.domain;

import com.pharmacy.repository.utils.FilterOptions;
import org.springframework.data.elasticsearch.core.FacetedPage;

/**
 * Created by Alexander on 07.02.2016.
 */
public class SearchResult<T> {

    private FacetedPage<T> page;
    private FilterOptions filterOptions;

    public SearchResult(FacetedPage<T> page, FilterOptions filterOptions){
        this.page = page;
        this.filterOptions = filterOptions;
    }

    public FacetedPage<T> getPage() {
        return page;
    }

    public void setPage(FacetedPage<T> page) {
        this.page = page;
    }

    public FilterOptions getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(FilterOptions filterOptions) {
        this.filterOptions = filterOptions;
    }
}
