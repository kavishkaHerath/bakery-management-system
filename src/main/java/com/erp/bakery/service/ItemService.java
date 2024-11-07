package com.erp.bakery.service;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.*;
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

    public ItemDTO findItemById(Long itemId) {
        return new ItemDTO(itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Supplier not found with Supplier Code: " + itemId)));
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

    // Update item details
    public Item updateItemDetails(Item updateRequest) {
        var itemId = updateRequest.getItemId();
        var itemName = updateRequest.getItemName();

        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found with Item code: " + itemId));
        // Check if item name is in use by another item
        if (itemName != null && !itemName.equals(existingItem.getItemName()) &&
                itemRepository.existsByItemName(itemName)) {
            throw new DuplicateFieldException("Item Name " + itemName + " is already in use.");
        }
        // Update only specified fields
        if (itemName != null) {
            existingItem.setItemName(itemName);
        }
        if (updateRequest.getDisplayName() != null) {
            existingItem.setDisplayName(updateRequest.getDisplayName());
        }
        if (updateRequest.getStatus() != null) {
            existingItem.setStatus(updateRequest.getStatus());
        }
        if (updateRequest.getImageUrl() != null) {
            existingItem.setImageUrl(updateRequest.getImageUrl());
        }
        if (updateRequest.getCategory() != null) {
            existingItem.setCategory(updateRequest.getCategory());
        }
        if (updateRequest.getSupplier() != null) {
            existingItem.setSupplier(updateRequest.getSupplier());
        }
        existingItem.setModifyBy(updateRequest.getModifyBy());
        existingItem.setModifyDate(LocalDate.now());
        return itemRepository.save(existingItem);
    }

    // Method to delete an item by ID
    public void deleteItemById(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new NotFoundException("Item not found with Item code: " + itemId);
        }
        itemRepository.deleteById(itemId);
    }
}
