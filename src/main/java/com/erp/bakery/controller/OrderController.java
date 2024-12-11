package com.erp.bakery.controller;

import com.erp.bakery.exception.AccessToModifyException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.Order;
import com.erp.bakery.model.dto.ItemGetByIdDTO;
import com.erp.bakery.model.dto.OderDTO;
import com.erp.bakery.model.dto.OrderDTOGet;
import com.erp.bakery.model.dto.OrderModify;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/itemOrders")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItemOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveItemOrder(order);
        return ResponseEntity.ok("Item Order created successfully with code: " + savedOrder.getOrderCode());
    }

    @GetMapping("/all-order-details")
    public List<OderDTO> getAllOrders() {
        return orderService.findAllOrderDetails();
    }

    @GetMapping("/all-order-details/{managerId}")
    public List<OderDTO> getAllOrdersByManagerId(@PathVariable String managerId) {
        return orderService.findAllOrderDetailsByManagerId(managerId);
    }

    @GetMapping("/get-order-details/{orderCode}")
    public ResponseEntity<?> getOrderByOrderNumber(@PathVariable String orderCode) {
        try {
            OrderDTOGet order = orderService.getOrderByOrderNumber(orderCode);
            return ResponseEntity.ok(order);
        } catch (NotFoundException ex) {
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editItemOrder(@RequestBody OrderModify order) {
        try {
            Order newItem = orderService.updateItemsDetails(order);
            ResponseMessage response = new ResponseMessage(
                    "success",
                    "Supplier updated successfully",
                    order.getOrderCode()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AccessToModifyException ex) {
            ResponseMessage error_duplicate = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    order.getOrderCode()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error_duplicate);
        } catch (Exception ex) {
            ResponseMessage error = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
