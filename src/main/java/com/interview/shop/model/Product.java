package com.interview.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    public double price;

    public int stock;

    public String status;

    public Product() {
    }
}
