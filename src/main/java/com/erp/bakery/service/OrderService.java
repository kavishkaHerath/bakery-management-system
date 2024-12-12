package com.erp.bakery.service;


import com.erp.bakery.exception.AccessToModifyException;
import com.erp.bakery.exception.DeletionException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.OrderDetail;
import com.erp.bakery.model.Order;
import com.erp.bakery.model.dto.ItemGetByIdDTO;
import com.erp.bakery.model.dto.OderDTO;
import com.erp.bakery.model.dto.OrderDTOGet;
import com.erp.bakery.model.dto.OrderModify;
import com.erp.bakery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    // private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
//        this.orderDetailRepository = orderDetailRepository;
    }

    public Order saveItemOrder(Order order) {
        // Generate orderCode
        var requestUser = order.getRequestBy().getUserId();
        String orderCode = generateItemOrderCode(requestUser);
        order.setOrderCode(orderCode);
        order.setRequestDate(LocalDate.now());

        if (requestUser.startsWith("ADM")) {
            order.setStatus("A");
            order.setApprovedDate(LocalDate.now());
        } else {
            order.setStatus("P");// If the status is 'P', the person who made this order can modify the requirements.
        }

        var numberOfItems = 0;
        var totalPriceOfItem = 0.00;

        for (OrderDetail item : order.getOrderDetails()) {
            numberOfItems++;
            var total = item.getUnitPrice() * item.getQuantity();
            totalPriceOfItem += total;
            item.setTotalPrice(total);
        }
        order.setNumberOfItems(numberOfItems);
        order.setTotalPrice(totalPriceOfItem);
        // Save order and associated orderDetails
        orderRepository.save(order);

        return order;
    }

    private String generateItemOrderCode(String requestBy) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        // Format the date as YYMMMDD (e.g., 24AUG08)
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyMMMdd", Locale.ENGLISH);
        String datePart = now.format(dateFormatter).toUpperCase();

        // Format the time as HHMM (e.g., 1040 for 10:40 AM)
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        String timePart = now.format(timeFormatter);

        // Generate custom Order Code
        return datePart + requestBy + timePart;
    }

    public List<OderDTO> findAllOrderDetails() {
        return orderRepository.findAllOrdersDetails();
    }

    public List<OderDTO> findAllOrderDetailsByManagerId(String managerId) {
        return orderRepository.findAllOrdersDetailsByManagerID(managerId);
    }

    public Order updateItemsDetails(OrderModify order) {
        Order existingOrder = orderRepository.findById(order.getOrderCode())
                .orElseThrow(() -> new RuntimeException("Items Order not found"));
        // Check if the status allows modification
        if ("A".equals(existingOrder.getStatus())) {
            throw new AccessToModifyException("Modification not allowed: status is 'APPROVED'!");
        }
        // Check if modifying user is allowed (assumes previous user validation logic)
        if (!order.getModifyUserID().startsWith("ADM") && !existingOrder.getRequestBy().getUserId().equals(order.getModifyUserID())) {
            throw new AccessToModifyException("You can't modify this.");
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body(");
        }
        var numberOfItems = 0;
        var totalPriceOfItem = 0.00;
        // Update main order fields
        existingOrder.getOrderDetails().clear();
        existingOrder.getOrderDetails().addAll(order.getOrderDetails());

        // Modify or update order details
        for (OrderDetail item : order.getOrderDetails()) {
            numberOfItems++;
            var total = item.getUnitPrice() * item.getQuantity();
            totalPriceOfItem += total;
            item.setTotalPrice(total);
        }
        existingOrder.setOrderDetails(order.getOrderDetails());
        existingOrder.setExpectedDate(order.getExpectedDate());
        existingOrder.setNumberOfItems(numberOfItems);
        existingOrder.setTotalPrice(totalPriceOfItem);
        existingOrder.setRequestDate(LocalDate.now());
        if (order.getModifyUserID().startsWith("ADM")) {
            existingOrder.setStatus("A");
            existingOrder.setApprovedDate(LocalDate.now());
        } else {
            existingOrder.setStatus("P");// If the status is 'P', the person who made this order can modify the requirements.
        }
        // Save the updated main order
        orderRepository.save(existingOrder);
        return existingOrder;
    }


    public OrderDTOGet getOrderByOrderNumber(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode).orElseThrow(
                () -> new RuntimeException("Order not found with order number: " + orderCode)
        );
        List<OrderDTOGet.OrderDetailDTO> orderDetails = order.getOrderDetails().stream()
                .map(orderDetail -> new OrderDTOGet.OrderDetailDTO(
                        orderDetail.getId(),
                        orderDetail.getItem().getItemId(),
                        orderDetail.getItem().getItemName(),
                        orderDetail.getQuantity(),
                        orderDetail.getUnitPrice(),
                        orderDetail.getTotalPrice(),
                        orderDetail.getRequestBy())).toList();
        return new OrderDTOGet(
                order.getOrderCode(),
                order.getExpectedDate(),
                order.getStatus(),
                order.getApprovedDate(),
                order.getNumberOfItems(),
                order.getTotalPrice(),
                order.getRequestDate(),
                orderDetails);
    }

    public void updateApproval(String orderCode) {
        Order existingOrder = orderRepository.findById(orderCode)
                .orElseThrow(() -> new RuntimeException("Items Order not found"));
        existingOrder.setStatus("A");
        existingOrder.setApprovedDate(LocalDate.now());
        orderRepository.save(existingOrder);
    }

    public void updateReject(String orderCode) {
        Order existingOrder = orderRepository.findById(orderCode)
                .orElseThrow(() -> new RuntimeException("Items Order not found"));
        existingOrder.setStatus("R");
        existingOrder.setApprovedDate(LocalDate.now());
        orderRepository.save(existingOrder);
    }

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
