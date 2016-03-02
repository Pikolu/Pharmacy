package com.pharmacy.service.impl;

import com.pharmacy.domain.Article;
import com.pharmacy.repository.ArticleRepository;
import com.pharmacy.repository.search.ArticleSearchRepository;
import com.pharmacy.service.api.ExporterService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Pharmacy GmbH
 * Created by Alexander on 28.02.2016.
 */
@Service
@Transactional
public class ExporterServiceImpl implements ExporterService {

    private static final int DEFAULT_SIZE = 1000;

    @Inject
    private ArticleRepository articleRepository;

    @Inject
    private ArticleSearchRepository articleSearchRepository;

    @Override
    public void exportArticles() {
        List<Article> articles = articleRepository.findArticlesForExport(new PageRequest(0, DEFAULT_SIZE));
        if (articles != null && !articles.isEmpty()) {
            articles.stream().forEach(e -> e.setExported(true));
            articleSearchRepository.save(articles);
            articleRepository.save(articles);
        }

    }
}
