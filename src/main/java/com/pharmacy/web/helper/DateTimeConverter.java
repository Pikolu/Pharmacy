package com.pharmacy.web.helper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Pharmacy GmbH
 * Created by Alexander on 18.04.2016.
 */
public class DateTimeConverter {

    private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";

    public static String convert(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }
}
