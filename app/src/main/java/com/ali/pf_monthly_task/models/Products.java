package com.ali.pf_monthly_task.models;

import java.io.Serializable;

public class Products implements Serializable {
    private float id;
    private String title;
    private float price;
    private String description;
    private String category;
    private String image;
    Rating rating;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public Rating getRating() {
        return rating;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }






}
