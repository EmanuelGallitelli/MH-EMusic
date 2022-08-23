package com.emusic.school.dtos;

import com.emusic.school.models.PurchaseOrder;

public class PurchaseOrderDTO {

    private long id;
    private MerchDTO merch;
    private Integer quantity;

    public PurchaseOrderDTO() {
    }

    public PurchaseOrderDTO(PurchaseOrder purchaseOrder) {
        this.id = purchaseOrder.getId();
        this.merch = new MerchDTO(purchaseOrder.getMerch());
        this.quantity = purchaseOrder.getQuantity();
    }

    public long getId() {
        return id;
    }

    public MerchDTO getMerch() {
        return merch;
    }

    public void setMerch(MerchDTO merch) {
        this.merch = merch;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
