package com.techgeeknext.model;

import javax.persistence.*;

@Entity
@Table(name = "Bids")
public class BidsDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private double bid_value;

    public double getBid_value() {
        return bid_value;
    }

    public void setBid_value(double bid_value) {
        this.bid_value = bid_value;
    }
}
