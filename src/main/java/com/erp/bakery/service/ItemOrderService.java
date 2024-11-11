package com.erp.bakery.service;

import com.erp.bakery.model.ItemOrderDetail;
import com.erp.bakery.model.ItemsOrder;
import com.erp.bakery.repository.ItemOrderDetailRepository;
import com.erp.bakery.repository.ItemOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
}
