package com.erp.bakery.service;

import com.erp.bakery.exception.AccessToModifyException;
import com.erp.bakery.model.OrderDetail;
import com.erp.bakery.model.Order;
import com.erp.bakery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
//    @Autowired
//    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
//        this.orderDetailRepository = orderDetailRepository;
    }

//    public Order saveItemOrder(Order order) {
//        // Generate orderCode
//        String orderCode = generateItemOrderCode(order.getRequestBy());
//        order.setOrderCode(orderCode);
//
//        order.setRequestDate(LocalDate.now());
//        if (order.getRequestBy().substring(0, 3).equals("ADM")) {
//            order.setStatus("A");
//        } else {
//            order.setStatus("P");// If the status is 'P', the person who made this order can modify the requirements.
//        }
//
//        var numberOfItems = 0;
//        var totalPriceOfItem = 0.00;
//
//        for (OrderDetail item : order.getOrderDetails()) {
//            numberOfItems++;
//            System.out.println(item.getId());
//            var total = item.getUnitPrice() * item.getQuantity();
//            totalPriceOfItem += total;
//            item.setTotalPrice(total);
//        }
//        order.setNumberOfItems(numberOfItems);
//        order.setTotalPrice(totalPriceOfItem);
//        // Save order and associated orderDetails
//        orderRepository.save(order);
//
//        return order;
//    }

//    private String generateItemOrderCode(String requestBy) {
//        // Get the current date and time
//        LocalDateTime now = LocalDateTime.now();
//        // Format the date as YYMMMDD (e.g., 24AUG08)
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyMMMdd", Locale.ENGLISH);
//        String datePart = now.format(dateFormatter).toUpperCase();
//
//        // Format the time as HHMM (e.g., 1040 for 10:40 AM)
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
//        String timePart = now.format(timeFormatter);
//
//        // Generate custom Order Code
//        return datePart + requestBy + timePart;
//    }
//
//    public Order updateItemsDetails(Order order, String modifyingUser) {
//        Order existingOrder = orderRepository.findById(order.getOrderCode())
//                .orElseThrow(() -> new RuntimeException("Items Order not found"));
//        // Check if the status allows modification
//        if (!"P".equals(existingOrder.getStatus())) {
//            throw new AccessToModifyException("Modification not allowed: status is not 'PENDING'!");
//        }
//        // Check if modifying user is allowed (assumes previous user validation logic)
//        if (!"admin".equals(modifyingUser) && !existingOrder.getRequestBy().equals(modifyingUser)) {
//            throw new AccessToModifyException("You can't modify this.");
//            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body(");
//        }
//        var numberOfItems = 0;
//        var totalPriceOfItem = 0.00;
//        // Update main order fields
//        existingOrder.getOrderDetails().clear();
//        existingOrder.getOrderDetails().addAll(order.getOrderDetails());
//
//        // Modify or update order details
//        for (OrderDetail item : order.getOrderDetails()) {
//            numberOfItems++;
//            var total = item.getUnitPrice() * item.getQuantity();
//            totalPriceOfItem += total;
//            item.setTotalPrice(total);
//        }
//        existingOrder.setOrderDetails(order.getOrderDetails());
//        existingOrder.setExpectedDate(order.getExpectedDate());
//        existingOrder.setNumberOfItems(numberOfItems);
//        existingOrder.setTotalPrice(totalPriceOfItem);
//
//        // Save the updated main order
//        orderRepository.save(existingOrder);
//        return existingOrder;
//    }

//    public void deleteItemDetails(Order order, String modifyingUser) {
//        Order existingOrder = orderRepository.findById(order.getOrderCode())
//                .orElseThrow(() -> new RuntimeException("Items Order not found"));
//        // Check if the status allows modification
//        if (!"P".equals(existingOrder.getStatus())) {
//            throw new AccessToModifyException("Modification not allowed: status is not 'PENDING'!");
//        }
//        // Check if modifying user is allowed (assumes previous user validation logic)
//        if (!"admin".equals(modifyingUser) && !existingOrder.getRequestBy().equals(modifyingUser)) {
//            throw new AccessToModifyException("You can't modify this.");
//            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body(");
//        }
//        var numberOfItems = 0;
//        var totalPriceOfItem = 0.00;
//        // Update main order fields
//        existingOrder.getOrderDetails().clear();
//        existingOrder.getOrderDetails().addAll(order.getOrderDetails());
//
//        // Modify or update order details
//        for (OrderDetail item : order.getOrderDetails()) {
//            numberOfItems++;
//            var total = item.getUnitPrice() * item.getQuantity();
//            totalPriceOfItem += total;
//            item.setTotalPrice(total);
//        }
//        existingOrder.setOrderDetails(order.getOrderDetails());
//        existingOrder.setExpectedDate(order.getExpectedDate());
//        existingOrder.setNumberOfItems(numberOfItems);
//        existingOrder.setTotalPrice(totalPriceOfItem);
//
//        // Save the updated main order
//        orderRepository.save(existingOrder);
//        return existingOrder;
//    }
}
