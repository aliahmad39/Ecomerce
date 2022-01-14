package com.ali.pf_monthly_task.models;

import java.io.Serializable;

public class Rating implements Serializable {
    public float rate;
    public float count;


    // Getter Methods

    public float getRate() {
        return rate;
    }

    public float getCount() {
        return count;
    }

    // Setter Methods

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setCount(float count) {
        this.count = count;
    }
}
