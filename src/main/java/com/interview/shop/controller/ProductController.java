package com.interview.shop.controller;

import com.interview.shop.model.Product;
import com.interview.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/all")
    public List<Product> all() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping("/save")
    public Product save(@RequestBody Product product) {
        if (product.status == null) {
            product.status = "ACTIVE";
        }
        return productRepository.save(product);
    }

    @GetMapping("/applyDiscount")
    public Product applyDiscount(@RequestParam Long id, @RequestParam double percent) {
        Product p = productRepository.findById(id).get();
        p.price = p.price - (p.price * percent / 100);
        return productRepository.save(p);
    }
}
