package com.pharmacy.repository.search;

import com.pharmacy.domain.Article;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by Alexander on 14.11.2015.
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long> {

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"name\" :\n" +
            "{\"query\" :\n" +
            "\"?*\",\"analyze_wildcard\" : true}}}}}\n")
    List<Article> findArticles(String name);
}
