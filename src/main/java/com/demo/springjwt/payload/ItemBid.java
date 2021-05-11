package com.demo.springjwt.payload;

public class ItemBid {
    private Integer item_id;
    private String item_name;
    private String description;
    private double bid_value;
    private Integer on_sale;
    private String source_address;
    private String destination_address;
    private String image;

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

    public Integer getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(Integer on_sale) {
        this.on_sale = on_sale;
    }

    public String getSource_address() {
        return source_address;
    }

    public void setSource_address(String source_address) {
        this.source_address = source_address;
    }

    public String getDestination_address() {
        return destination_address;
    }

    public void setDestination_address(String destination_address) {
        this.destination_address = destination_address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
