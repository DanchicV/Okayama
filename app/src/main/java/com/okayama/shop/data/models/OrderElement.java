package com.okayama.shop.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderElement {

    @SerializedName("elementId")
    @Expose
    long elementId;

    @SerializedName("amount")
    @Expose
    float amount;

    public OrderElement() {
    }

    public OrderElement(long elementId, float amount) {
        this.elementId = elementId;
        this.amount = amount;
    }

    public long getElementId() {
        return elementId;
    }

    public void setElementId(long elementId) {
        this.elementId = elementId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
