package com.interview.shop;

import com.interview.shop.model.Customer;
import com.interview.shop.model.Product;
import com.interview.shop.repository.CustomerRepository;
import com.interview.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public DataLoader(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        Product p1 = new Product();
        p1.name = "Keyboard";
        p1.price = 29.99;
        p1.stock = 100;
        p1.status = "ACTIVE";
        productRepository.save(p1);

        Product p2 = new Product();
        p2.name = "Monitor";
        p2.price = 199.99;
        p2.stock = 50;
        p2.status = "ACTIVE";
        productRepository.save(p2);

        Customer c = new Customer();
        c.name = "John Doe";
        c.email = "john@example.com";
        c.password = "123456";
        c.creditCardNumber = "4111111111111111";
        customerRepository.save(c);
    }
}
