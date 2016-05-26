package com.pharmacy.web.helper;

import com.pharmacy.domain.Price;
import org.apache.commons.math3.util.Precision;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by Alexander on 03.01.2016.
 */
public class ArticleHelper {

    public Optional<Price> getBestDiscount(List<Price> prices) {
        Comparator<Price> priceComparator = (e1, e2) -> Double.compare(
                e1.getPrice(), e2.getPrice());
        return prices.stream().min(priceComparator);
    }


    public static void sortPrice(List<Price> prices) {
        Collections.sort(prices, new Comparator<Price>() {

            @Override
            public int compare(Price o1, Price o2) {
                return (o1.getPrice() < o2.getPrice() ? -1 : (o1.getPrice() == o2.getPrice() ? 0 : 1));
            }
        });
    }

    public String round(Double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat decimalFormat = (DecimalFormat)nf;
        if (value == null){
            return "0.00";
        }
        String result = decimalFormat.format(bigDecimal);
        if (!result.contains(",")){
            result += ".00";
        }
        return result;
    }
}
