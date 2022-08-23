package com.emusic.school.dtos;

import com.emusic.school.models.MerchWaist;

public class EditMerchDTO {
    private long id;
    private int stock;
    private String type;
    private double price;
    private MerchWaist waist;

    public EditMerchDTO() {
    }

    public long getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public String getType() {
        return type;
    }


    public double getPrice() {
        return price;
    }


    public MerchWaist getWaist() {
        return waist;
    }

}
