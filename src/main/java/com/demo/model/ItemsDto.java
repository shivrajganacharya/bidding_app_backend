package com.demo.model;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class ItemsDto {

    private String item_name;
    private String description;
    private Integer base_price;
    private Date transport_date;
    private Integer on_sale;
    private Integer max_bid_id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    public Integer getMax_bid_id() {
        return max_bid_id;
    }

    public void setMax_bid_id(Integer max_bid_id) {
        this.max_bid_id = max_bid_id;
    }

    public Integer getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(Integer on_sale) {
        this.on_sale = on_sale;
    }

    public Date getTransport_date() {
        return transport_date;
    }

    public void setTransport_date(Date transport_date) {
        this.transport_date = transport_date;
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

    public Integer getBase_price() {
        return base_price;
    }

    public void setBase_price(Integer base_price) {
        this.base_price = base_price;
    }
}
