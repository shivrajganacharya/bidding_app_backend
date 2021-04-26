package com.demo.springjwt.models;

import javax.persistence.*;

@Entity
@Table(name = "Bids")
public class Bids implements Comparable<Bids> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private double bid_value;


    public Integer getId() {
        return id;
    }

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

    @Override
    public int compareTo(Bids b) {
        if(getBid_value() > b.getBid_value()) {
            return 1;
        } else {
            return 0;
        }
    }
}
