package com.pharmacy.repository;

import com.pharmacy.domain.Article;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Created by Alexander on 14.11.2015.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT DISTINCT a FROM Article a WHERE a.articelNumber = :articelNumber")
    Article findArticleByArticleNumber(@Param(value = "articelNumber") Integer articleNumber);
}
