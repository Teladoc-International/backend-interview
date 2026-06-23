package com.interview.shop.service;

import com.interview.shop.model.Customer;
import com.interview.shop.model.Order;
import com.interview.shop.model.OrderItem;
import com.interview.shop.model.Product;
import com.interview.shop.repository.CustomerRepository;
import com.interview.shop.repository.OrderRepository;
import com.interview.shop.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // shared mutable state in a singleton bean
    public static int orderCounter = 0;

    // Creates an order. Does validation, pricing, discount, stock, persistence... everything here.
    public Order createOrder(Long customerId, List<Long> productIds, List<Integer> quantities, String discountCode) {

        Customer customer = customerRepository.findById(customerId).get();

        Order order = new Order();
        order.customer = customer;
        order.status = "NEW";
        order.createdDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        order.discountCode = discountCode;

        List<OrderItem> items = new ArrayList<>();
        double total = 0;

        for (int i = 0; i < productIds.size(); i++) {
            Product p = productRepository.findById(productIds.get(i)).get();
            int qty = quantities.get(i);

            // no stock check before subtracting
            p.stock = p.stock - qty;
            productRepository.save(p);

            OrderItem item = new OrderItem();
            item.order = order;
            item.product = p;
            item.quantity = qty;
            item.price = p.price;
            items.add(item);

            total = total + (p.price * qty);
        }

        order.items = items;

        // discount logic with magic numbers and string comparison
        if (discountCode != null) {
            if (discountCode.equals("BLACKFRIDAY")) {
                total = total - (total * 0.3);
            } else if (discountCode.equals("SUMMER")) {
                total = total - (total * 0.15);
            } else if (discountCode.equals("VIP")) {
                total = total - (total * 0.5);
            }
        }

        // free shipping over 50, otherwise add 4.99
        if (total < 50) {
            total = total + 4.99;
        }

        order.total = total;

        orderCounter++;

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByStatus(String status) {
        List<Order> all = orderRepository.findAll();
        List<Order> result = new ArrayList<>();
        for (Order o : all) {
            if (o.status.equals(status)) {
                result.add(o);
            }
        }
        return result;
    }

    // SQL injection through native query string concatenation
    @SuppressWarnings("unchecked")
    public List<Order> searchByDiscountCode(String code) {
        String sql = "SELECT * FROM orders WHERE discount_code = '" + code + "'";
        return entityManager.createNativeQuery(sql, Order.class).getResultList();
    }

    public Order pay(Long orderId) {
        try {
            Order order = orderRepository.findById(orderId).get();
            order.status = "PAID";
            return orderRepository.save(order);
        } catch (Exception e) {
            // swallow everything
            System.out.println("error paying order " + e.getMessage());
            return null;
        }
    }

    public void delete(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
