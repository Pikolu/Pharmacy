package com.pharmacy.repository;

import com.pharmacy.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Alexander on 14.11.2015.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT DISTINCT a FROM Article a WHERE a.articelNumber = :articelNumber")
    Article findArticleByArticleNumber(@Param(value = "articelNumber") Integer articleNumber);

    @Query("SELECT a FROM Article a LEFT JOIN a.prices p ORDER BY p.discount DESC")
    List<Article> loadBestDiscountedArticles(Pageable pageable);

    @Query("SELECT a FROM Article a WHERE a.exported = false")
    List<Article> findArticlesForExport(Pageable pageable);

    @Query("SELECT a.id, a.name FROM Article a")
    List<Object[]> findAllForSiteMap();
}
