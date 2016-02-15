package com.pharmacy.repository.utils;

/**
 * Created by Alexander on 01.02.2016.
 */
public enum SortOrder {

    RELEVANCE("Relevanz"),
    NAME_ASC("Name A-Z"),
    NAME_DESC("Name Z-A"),
    PRICE_ASC("Preis aufsteigend"),
    PRICE_DESC("Preis absteigend");

    private String value;

    SortOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
