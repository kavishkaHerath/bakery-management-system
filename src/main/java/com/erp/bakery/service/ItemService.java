package com.erp.bakery.service;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.model.EmployeeDTO;
import com.erp.bakery.model.Item;
import com.erp.bakery.model.ItemDTO;
import com.erp.bakery.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<ItemDTO> findAllItemDetails() {
        return itemRepository.findAll().stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());
    }

    public Item addItem(Item item) {
        // Check for duplicate email and phone
        if (itemRepository.existsByItemName(item.getItemName())) {
            throw new DuplicateFieldException("Item name " + item.getItemName() + " is already in use.");
        }
        // Set to current date without time
        item.setAddDate(LocalDate.now());
        return itemRepository.save(item);
    }
}
