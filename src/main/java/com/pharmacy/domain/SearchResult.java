package com.pharmacy.domain;

import com.pharmacy.repository.utils.SortOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Alexander on 07.02.2016.
 */
@Getter
@Setter
@ToString
@Slf4j
public class SearchResult<T> {

    private static final int PAGE_COUNT = 10;
    private static final int SIZE_PER_PAGE = 20;

    private FacetedPage<T> facetedPage;
    private List<String> pharmacies;
    private String parameter;
    private SortOrder sortOrder = SortOrder.RELEVANCE;
    private int firstPage = 1;
    private int currentPage = 1;
    private int lastPage = 10;
    private Page<T> searchPage;

    public SearchResult() {

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

    public void buildPagination(int page, ModelAndView model, Long size) {
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
        model.addObject("currentPage", currentPage);
        model.addObject("firstPage", firstPage);
        model.addObject("lastPage", lastPage);
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
}
