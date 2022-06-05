
package com.anna.healthyfoods.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("food")
    @Expose
    private String food;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("foodCategory")
    @Expose
    private String foodCategory;
    @SerializedName("foodId")
    @Expose
    private String foodId;
    @SerializedName("image")
    @Expose
    private String image;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Ingredient() {
    }

    /**
     * 
     * @param image
     * @param quantity
     * @param measure
     * @param foodId
     * @param weight
     * @param text
     * @param foodCategory
     * @param food
     */
    public Ingredient(String text, Double quantity, String measure, String food, Integer weight, String foodCategory, String foodId, String image) {
        super();
        this.text = text;
        this.quantity = quantity;
        this.measure = measure;
        this.food = food;
        this.weight = weight;
        this.foodCategory = foodCategory;
        this.foodId = foodId;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
