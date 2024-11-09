package com.erp.bakery.controller;

import com.erp.bakery.model.ItemsOrder;
import com.erp.bakery.service.ItemOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
