package com.pharmacy.service.api;

import com.pharmacy.domain.Article;
import com.pharmacy.exceptions.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.FacetedPage;

import java.util.List;

/**
 * Created by Alexander on 14.11.2015.
 */
public interface ArticleService {

    Page<Article> findArticlesByBestPrice(Pageable pageable);

    FacetedPage<Article> findArticlesByParameter(String parameter, Pageable pageable);

    Article findArticleByArticleNumber(Long id) throws ServiceException;
}
