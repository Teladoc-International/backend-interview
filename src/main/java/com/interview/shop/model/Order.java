package com.interview.shop.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    public Customer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<OrderItem> items = new ArrayList<>();

    public String status;

    public double total;

    public String createdDate;

    public String discountCode;

    public Order() {
    }
}
