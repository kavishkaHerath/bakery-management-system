package com.erp.bakery.service;

import com.erp.bakery.exception.DeletionException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.*;
import com.erp.bakery.model.dto.ItemDTO;
import com.erp.bakery.model.dto.ItemGetByIdDTO;
import com.erp.bakery.model.dto.SupplierGetDTO;
import com.erp.bakery.repository.ItemRepository;
import com.erp.bakery.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final OrderDetailRepository orderDetailRepository;

    public ItemService(ItemRepository itemRepository, OrderDetailRepository orderDetailRepository) {
        this.itemRepository = itemRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<ItemDTO> findAllItemDetails() {
        return itemRepository.findAllItemsDetails();
    }

//    public Optional<ItemGetByIdDTO> findItemById(Long itemId) {
//        return Optional.ofNullable(itemRepository.findItemDetailsById(itemId).orElseThrow(
//                () -> new NotFoundException("Items not found with Item Code: " + itemId)
//        ));
//    }

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
        existingItem.setModifiedEmployee(updateRequest.getModifiedEmployee());
        existingItem.setModifyDate(LocalDate.now());
        return itemRepository.save(existingItem);
    }

    // Method to delete an item by ID
    public void deleteItemById(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new NotFoundException("Item not found with Item code: " + itemId);
        }
        // Check if the item is referenced in the Order table
        boolean isReferenced = orderDetailRepository.existsByItem_ItemId(itemId);
        if (isReferenced) {
            throw new DeletionException(
                    "Item with Item ID :" + itemId + " is associated with orders and cannot be deleted."
            );
        }
        itemRepository.deleteById(itemId);
    }

    public List<ItemActiveDTO> getActiveItemsBySupplier(Long supplierCode) {
        return itemRepository.findActiveItemsBySupplier(supplierCode);
    }
}
