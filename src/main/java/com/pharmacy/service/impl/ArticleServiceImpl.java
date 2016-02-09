package com.pharmacy.service.impl;

import com.pharmacy.domain.Article;
import com.pharmacy.repository.ArticleRepository;
import com.pharmacy.repository.search.ArticleSearchRepository;
import com.pharmacy.repository.search.PriceSearchRepository;
import com.pharmacy.repository.utils.FilterOptions;
import com.pharmacy.service.api.ArticleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.collect.Collections2;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.facet.FacetRequest;
import org.springframework.data.elasticsearch.core.facet.FacetResult;
import org.springframework.data.elasticsearch.core.facet.request.NativeFacetRequest;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


/**
 * Created by Alexander on 14.11.2015.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Inject
    private ArticleSearchRepository articleSearchRepository;
    @Inject
    private ArticleRepository articleRepository;
    @Inject
    private PriceSearchRepository priceSearchRepository;

    @Override
    public Page<Article> findArticlesByBestPrice(Pageable pageable) {
        Page<Article> page = articleSearchRepository.findAll(pageable);
        return page;
    }

    @Override
    public FacetedPage<Article> findArticlesByParameter(String parameter, Pageable pageable, FilterOptions filterOptions) {

        //Sort
        SortBuilder sortBuilder = new FieldSortBuilder("prices.price").order(SortOrder.ASC);

        //Facet builder for pharmacy names
        TermsFacetBuilder termsFacetBuilder = new TermsFacetBuilder("prices.pharmacy.name");
        termsFacetBuilder.field("prices.pharmacy.name");
        FacetRequest facetRequest = new NativeFacetRequest(termsFacetBuilder);

        //creates query for elastic search
        QueryBuilder queryBuilder;
        if (StringUtils.isBlank(parameter)) {
            queryBuilder = QueryBuilders.wildcardQuery("name", "*");
        } else {
            queryBuilder = QueryBuilders.wildcardQuery("name", "*" + parameter.toLowerCase() + "*");
        }

        // build filter for the elastic search
        FilterBuilder filterBuilder= null;
        if (CollectionUtils.isNotEmpty(filterOptions.getPharmacies())) {
            filterBuilder = buildAndFilter("prices.pharmacy.name", filterOptions.getPharmacies());

        }

        SearchQuery searchQuery = buildSearchQuery(queryBuilder, facetRequest, filterBuilder, pageable, sortBuilder);
        FacetedPage<Article> articles = articleSearchRepository.search(searchQuery);

        for (FacetResult facetResult : articles.getFacets()) {
//            System.out.println(facetResult);
            if (facetResult instanceof TermResult) {
                TermResult termResult = (TermResult) facetResult;
                for (Term term : termResult.getTerms()) {
                    System.out.println(term.getTerm() + " : " + term.getCount());
                }

            }
        }

        return articles;
    }

    private SearchQuery buildSearchQuery(QueryBuilder queryBuilder, FacetRequest facetRequest, FilterBuilder filterBuilder, Pageable pageable, SortBuilder sortBuilder) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(queryBuilder).
                withFacet(facetRequest).
                withFilter(filterBuilder).
                withPageable(pageable).
                withSort(sortBuilder).
                build();
        return searchQuery;
    }

    private OrFilterBuilder buildAndFilter(String name, List<String> values) {
        OrFilterBuilder filterBuilder = FilterBuilders.orFilter();

        values.forEach(e -> {
            TermFilterBuilder termFilterBuilder = FilterBuilders.termFilter(name, e);
            filterBuilder.add(termFilterBuilder);
        });

        return filterBuilder;
    }

    @Override
    public Article findArticleByArticleNumber(Long id) {
        Assert.notNull(id);
        return articleSearchRepository.findOne(id);
    }
}
