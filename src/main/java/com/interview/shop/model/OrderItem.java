package com.interview.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    public Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    public Product product;

    public int quantity;

    public double price;

    public OrderItem() {
    }
}
