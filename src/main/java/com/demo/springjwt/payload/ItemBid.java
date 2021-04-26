package com.demo.springjwt.payload;

public class ItemBid {
    private Integer item_id;
    private String item_name;
    private String description;
    private double bid_value;

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBid_value() {
        return bid_value;
    }

    public void setBid_value(double bid_value) {
        this.bid_value = bid_value;
    }
}
