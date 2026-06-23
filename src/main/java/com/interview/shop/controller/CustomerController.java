package com.interview.shop.controller;

import com.interview.shop.model.Customer;
import com.interview.shop.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Value("${app.api.key}")
    private String apiKey;

    // returns all customers including password and credit card number
    @GetMapping("/list")
    public List<Customer> list() {
        return customerRepository.findAll();
    }

    @PostMapping("/register")
    public Customer register(@RequestBody Customer customer) {
        // password saved as-is, no hashing, no email validation
        return customerRepository.save(customer);
    }

    // login by comparing plain text password, returns the whole entity
    @PostMapping("/login")
    public Customer login(@RequestParam String email, @RequestParam String password) {
        List<Customer> all = customerRepository.findAll();
        for (Customer c : all) {
            if (c.email.equals(email) && c.password.equals(password)) {
                return c;
            }
        }
        return null;
    }

    // manual API key check duplicated logic, hardcoded comparison
    @GetMapping("/admin")
    public List<Customer> admin(@RequestParam String key) {
        if (key.equals("SECRET-12345-ADMIN")) {
            return customerRepository.findAll();
        }
        return null;
    }
}
