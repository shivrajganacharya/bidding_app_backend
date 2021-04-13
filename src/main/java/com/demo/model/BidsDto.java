package com.demo.model;

public class BidsDto {
    private String username;
    private double bid_value;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBid_value() {
        return bid_value;
    }

    public void setBid_value(double bid_value) {
        this.bid_value = bid_value;
    }
}
