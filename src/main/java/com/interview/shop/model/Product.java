package com.interview.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String name;

    // money as double
    public double price;

    public int stock;

    // "ACTIVE" / "INACTIVE" as plain string instead of enum
    public String status;

    public Product() {
    }
}
