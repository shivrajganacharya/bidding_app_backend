package com.demo.springjwt.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer item_id;
    private String item_name;
    private String description;
    private Integer base_price;
    private Integer on_sale;
    private Integer max_bid_id;
    private String source_address;
    private String destination_address;
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id_fk", referencedColumnName = "item_id")
    List<Bids> bids  = new ArrayList<Bids>();


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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public List<Bids> getBids() {
        return bids;
    }

    public void setBids(List<Bids> bids) {
        this.bids = bids;
    }

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

    public Integer getBase_price() {
        return base_price;
    }

    public void setBase_price(Integer base_price) {
        this.base_price = base_price;
    }

    public Date getTransport_date() {
        return datetime;
    }

    public void setTransport_date(Date transport_date) {
        this.datetime = transport_date;
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

