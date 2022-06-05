
package com.anna.healthyfoods.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CHOCDFNet {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("unit")
    @Expose
    private String unit;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CHOCDFNet() {
    }

    /**
     * 
     * @param unit
     * @param quantity
     * @param label
     */
    public CHOCDFNet(String label, Integer quantity, String unit) {
        super();
        this.label = label;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
