package com.pharmacy.service.api;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.exceptions.PersistenceException;
import com.pharmacy.exceptions.ServiceException;
import com.pharmacy.repository.utils.FilterOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.FacetedPage;

import java.util.List;

/**
 * Created by Alexander on 14.11.2015.
 */
public interface ArticleService {

    Page<Article> findArticlesByBestPrice(Pageable pageable);

    FacetedPage<Article> findArticlesByParameter(String parameter, Pageable pageable, SearchResult searchResult);

    Article findArticleByArticleNumber(Long id) throws ServiceException;

    List<Article> loadBestDiscountedArticles();
}
