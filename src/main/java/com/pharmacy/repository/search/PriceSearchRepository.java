package com.pharmacy.repository.search;

import com.pharmacy.domain.Price;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by Alexander on 02.01.2016.
 */
public interface PriceSearchRepository extends ElasticsearchRepository<Price, Long> {


}
