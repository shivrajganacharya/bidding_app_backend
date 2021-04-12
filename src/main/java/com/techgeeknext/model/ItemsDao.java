package com.techgeeknext.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Items")
public class ItemsDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer item_id;
    private String item_name;
    private String description;
    private Integer base_price;
    private Integer on_sale;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id_fk", referencedColumnName = "item_id")
    List<BidsDao> bids  = new ArrayList<BidsDao>();


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

    public List<BidsDao> getBids() {
        return bids;
    }

    public void setBids(List<BidsDao> bids) {
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
}

