package com.emusic.school.dtos;

import com.emusic.school.models.Merch;
import com.emusic.school.models.MerchWaist;


public class MerchDTO {
    private long id;
    private int stock;
    private String type;
    private double price;
    private MerchWaist waist;
    private String urlImage;
    private boolean isActive;

    public MerchDTO() {
    }

    public MerchDTO(Merch merch) {
        this.id = merch.getId();
        this.stock = merch.getStock();
        this.type = merch.getType();
        this.price = merch.getPrice();
        this.waist = merch.getWaist();
        this.isActive = merch.isActive();
        this.urlImage = merch.getUrlImage();
    }

    public long getId() {
        return id;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
