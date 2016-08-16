package com.pharmacy.service.impl;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.Price;
import com.pharmacy.repository.PriceRepository;
import com.pharmacy.service.api.PriceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Pharmacy GmbH 
 * Created by Alexander on 16.08.2016.
 */

@Service
public class PriceServiceImpl implements PriceService {

    @Inject
    private PriceRepository priceRepository;

    @Override
    public List<Price> loadPricesForArticle(Article article) {
        return priceRepository.loadPricesForArticle(article);
    }
}
