package com.techgeeknext.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;

    @Column
    private String firstName;

    @Column
    private String email;

    @Column
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id_fk", referencedColumnName = "id")
    List<ItemsDao> items = new ArrayList<ItemsDao>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id_fk", referencedColumnName = "id")
    List<BidsDao> bids  = new ArrayList<BidsDao>();

    public List<ItemsDao> getItems() {
        return items;
    }

    public void setItems(List<ItemsDao> items) {
        this.items = items;
    }

    public List<BidsDao> getBids() {
        return bids;
    }

    public void setBids(List<BidsDao> bids) {
        this.bids = bids;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

