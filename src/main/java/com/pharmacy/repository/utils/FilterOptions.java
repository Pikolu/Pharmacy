package com.pharmacy.repository.utils;

/**
 * Created by Alexander on 01.02.2016.
 */
public class FilterOptions {

    private String[] pharmacies;

    private SortOrder sortOrder = SortOrder.UNSORTED;

    public String[] getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(String[] pharmacies) {
        this.pharmacies = pharmacies;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
