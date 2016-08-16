package com.pharmacy.service.api;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.Price;

import java.util.List;

/**
 * Pharmacy GmbH
 * Created by Alexander on 16.08.2016.
 */
public interface PriceService {

    List<Price> loadPricesForArticle(Article article);
}
