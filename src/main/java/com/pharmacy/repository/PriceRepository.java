package com.pharmacy.repository;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Alexander on 03.01.2016.
 */
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT DISTINCT p FROM Price p WHERE p.article = :article")
    List<Price> loadPricesForArticle(@Param(value = "article") Article article);

}
