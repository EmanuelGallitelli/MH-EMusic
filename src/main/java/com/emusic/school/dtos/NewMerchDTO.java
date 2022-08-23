package com.emusic.school.dtos;

import com.emusic.school.models.MerchWaist;

public class NewMerchDTO {

    private int stock;
    private String type;
    private double price;
    private String urlImage;
    private MerchWaist waist;


    public NewMerchDTO() {
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public MerchWaist getWaist() {
        return waist;
    }
    public void setWaist(MerchWaist waist) {
        this.waist = waist;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
