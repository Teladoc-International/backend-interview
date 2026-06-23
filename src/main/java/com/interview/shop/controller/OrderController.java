package com.interview.shop.controller;

import com.interview.shop.model.Order;
import com.interview.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public Order createOrder(@RequestBody Map<String, Object> body) {

        Long customerId = Long.valueOf(body.get("customerId").toString());
        List<Long> productIds = (List<Long>) body.get("productIds");
        List<Integer> quantities = (List<Integer>) body.get("quantities");
        String discountCode = (String) body.get("discountCode");

        return orderService.createOrder(customerId, productIds, quantities, discountCode);
    }

    @GetMapping("/getOrdersByStatus")
    public List<Order> getOrdersByStatus(@RequestParam String status) {
        return orderService.getOrdersByStatus(status);
    }

    @GetMapping("/searchOrders")
    public List<Order> search(@RequestParam String discountCode) {
        return orderService.searchByDiscountCode(discountCode);
    }

    @GetMapping("/payOrder/{id}")
    public Order pay(@PathVariable Long id) {
        return orderService.pay(id);
    }

    @GetMapping("/deleteOrder/{id}")
    public String delete(@PathVariable Long id) {
        orderService.delete(id);
        return "ok";
    }
}
