package com.erp.bakery.controller;

import com.erp.bakery.exception.AccessToModifyException;
import com.erp.bakery.model.Order;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.ItemOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/itemOrders")
@CrossOrigin("*")
public class ItemOrderController {
    @Autowired
    private ItemOrderService itemOrderService;

    @PostMapping("/add")
    public ResponseEntity<String> addItemOrder(@RequestBody Order order) {
        Order savedOrder = itemOrderService.saveItemOrder(order);
        return ResponseEntity.ok("Item Order created successfully with code: " + savedOrder.getOrderCode());
    }

    @PutMapping("/edit/{modifyingUser}")
    public ResponseEntity<?> editItemOrder(@RequestBody Order order, @PathVariable String modifyingUser) {
        try {
            Order newItem = itemOrderService.updateItemsDetails(order, modifyingUser);
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
