package com.interview.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    public String email;

    // stored in plain text
    public String password;

    public String creditCardNumber;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public java.util.List<Order> orders = new java.util.ArrayList<>();

    public Customer() {
    }
}
