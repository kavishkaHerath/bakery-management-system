package com.erp.bakery.controller;

import com.erp.bakery.exception.AccessToModifyException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.model.Item;
import com.erp.bakery.model.ItemsOrder;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.ItemOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/itemOrders")
public class ItemOrderController {
    @Autowired
    private ItemOrderService itemOrderService;

    @PostMapping("/add")
    public ResponseEntity<String> addItemOrder(@RequestBody ItemsOrder itemsOrder) {
        ItemsOrder savedOrder = itemOrderService.saveItemOrder(itemsOrder);
        return ResponseEntity.ok("Item Order created successfully with code: " + savedOrder.getItemOrderCode());
    }

    @PutMapping("/edit/{modifyingUser}")
    public ResponseEntity<?> editItemOrder(@RequestBody ItemsOrder itemsOrder, @PathVariable String modifyingUser) {
        try {
            ItemsOrder newItem = itemOrderService.updateItemsDetails(itemsOrder, modifyingUser);
            ResponseMessage response = new ResponseMessage(
                    "success",
                    "Supplier updated successfully",
                    itemsOrder.getItemOrderCode()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AccessToModifyException ex) {
            ResponseMessage error_duplicate = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    itemsOrder.getItemOrderCode()
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
