package com.sdet.framework.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPojo {

    private String title;
    private String description;
    private double price;
    private String category;
    private int qty;
    private String imgSrc;

    // 1. Default (No-Args) Constructor (Required by Jackson for deserialization)
    public ProductPojo() {
    }

    // 2. Parameterized (All-Args) Constructor (Optional, helpful for quick initialization)
    public ProductPojo(String title, String description, Double price, String category, int qty, String ram, String imgSrc) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.qty = qty;
        this.imgSrc = imgSrc;
    }

    // 3. Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }



    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}