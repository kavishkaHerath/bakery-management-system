package com.erp.bakery.service;

import com.erp.bakery.exception.AccessToModifyException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.model.ItemOrderDetail;
import com.erp.bakery.model.ItemsOrder;
import com.erp.bakery.repository.ItemOrderDetailRepository;
import com.erp.bakery.repository.ItemOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ItemOrderService {
    @Autowired
    private ItemOrderRepository itemOrderRepository;
    private ItemOrderDetailRepository itemOrderDetailRepository;

    public ItemsOrder saveItemOrder(ItemsOrder itemOrder) {
        // Generate itemOrderCode
        String itemOrderCode = generateItemOrderCode(itemOrder.getRequestBy());
        itemOrder.setItemOrderCode(itemOrderCode);

        itemOrder.setRequestDate(LocalDate.now());
        itemOrder.setStatus("P"); // If the status is 'P', the person who made this order can modify the requirements.

        var numberOfItems = 0;
        var totalPriceOfItem = 0.00;

        for (ItemOrderDetail item : itemOrder.getItemOrderDetails()) {
            numberOfItems++;
            System.out.println(item.getId());
            var total = item.getUnitPrice() * item.getQuantity();
            totalPriceOfItem += total;
            item.setTotalPrice(total);
        }
        itemOrder.setNumberOfItems(numberOfItems);
        itemOrder.setTotalPrice(totalPriceOfItem);
        // Save itemOrder and associated itemOrderDetails
        itemOrderRepository.save(itemOrder);

        return itemOrder;
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

    public ItemsOrder updateItemsDetails(ItemsOrder itemsOrder, String modifyingUser) {
        ItemsOrder existingOrder = itemOrderRepository.findById(itemsOrder.getItemOrderCode())
                .orElseThrow(() -> new RuntimeException("Items Order not found"));

        // Check if the status allows modification
        if (!"P".equals(existingOrder.getStatus())) {
            throw new AccessToModifyException("Modification not allowed: status is not 'PENDING'!");
        }
// Check if modifying user is allowed (assumes previous user validation logic)
        if (!"admin".equals(modifyingUser) && !existingOrder.getRequestBy().equals(modifyingUser)) {
            throw new AccessToModifyException("You can't modify this.");
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body(");
        }
        var numberOfItems = 0;
        var totalPriceOfItem = 0.00;
        // Update main order fields

        existingOrder.getItemOrderDetails().clear();
        existingOrder.getItemOrderDetails().addAll(itemsOrder.getItemOrderDetails());

        List<ItemOrderDetail> itemOrderDetails;
        // Modify or update order details
        for (ItemOrderDetail item : itemsOrder.getItemOrderDetails()) {
            numberOfItems++;
            var total = item.getUnitPrice() * item.getQuantity();
            totalPriceOfItem += total;
            item.setTotalPrice(total);
        }
        existingOrder.setItemOrderDetails(itemsOrder.getItemOrderDetails());
        existingOrder.setExpectedDate(itemsOrder.getExpectedDate());
        existingOrder.setNumberOfItems(numberOfItems);
        existingOrder.setTotalPrice(totalPriceOfItem);


        itemOrderRepository.save(existingOrder);
        // Save the updated main order

        return existingOrder;
    }
}
