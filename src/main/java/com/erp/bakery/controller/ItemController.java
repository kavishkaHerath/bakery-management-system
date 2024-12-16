package com.erp.bakery.controller;

import com.erp.bakery.exception.DeletionException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.*;
import com.erp.bakery.model.dto.ItemDTO;
import com.erp.bakery.model.dto.ItemGetByIdDTO;
import com.erp.bakery.model.dto.ItemPriceDTO;
import com.erp.bakery.model.dto.OderDTO;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@CrossOrigin("*")
public class ItemController {
    @Autowired
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody Item item) {
        try {
            Item newItem = itemService.addItem(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
        } catch (DuplicateFieldException ex) {
            ResponseMessage error_duplicate = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    null
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

    @GetMapping("/all-item-details")
    public List<ItemDTO> getAllItems() {
        return itemService.findAllItemDetails();
    }

    @GetMapping("/getItem/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable Long itemId) {
        try {
            Optional<ItemGetByIdDTO> item = itemService.findItemById(itemId);
            return ResponseEntity.ok(item);
        } catch (NotFoundException ex) {
            // Handle item not found
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }

    @PutMapping("/editItemDetails")
    public ResponseEntity<ResponseMessage> updateItemDetails(@RequestBody Item updateRequest) {
        try {
            Item updatedItem = itemService.updateItemDetails(updateRequest);
            ResponseMessage response = new ResponseMessage(
                    "success",
                    "Item updated successfully",
                    updatedItem.getItemId().toString()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DuplicateFieldException ex) {
            ResponseMessage responseMessage = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    updateRequest.getItemId().toString()
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseMessage);
        } catch (Exception ex) {
            // Handle error, return appropriate error response
            ResponseMessage response = new ResponseMessage(
                    "error",
                    "Failed to update item: " + ex.getMessage(),
                    null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Method to delete item by ID
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        try {
            itemService.deleteItemById(itemId); // Call service to delete the item
            return ResponseEntity.status(HttpStatus.OK).body("Item with Item Code " + itemId + " deleted successfully.");
        } catch (NotFoundException ex) {
            // Handle item not found
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null  // No item code for error response
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        } catch (DeletionException ex) {
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    itemId.toString()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }

    @GetMapping("/get/active-by-supplier/{supplierCode}")
    public ResponseEntity<List<ItemActiveDTO>> getActiveItemsBySupplier(@PathVariable Long supplierCode) {
        List<ItemActiveDTO> items = itemService.getActiveItemsBySupplier(supplierCode);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/add-item-price")
    public ResponseEntity<?> addItemPrice(@RequestBody ItemPrice itemPrice) {
        try {
            ItemPrice newItemPrice = itemService.addItemPrice(itemPrice);
            return ResponseEntity.status(HttpStatus.CREATED).body(newItemPrice);
        } catch (DuplicateFieldException ex) {
            ResponseMessage error_duplicate = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    null
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

    @GetMapping("/all-items-with-price")
    public List<ItemPriceDTO> getAllItemsWithPrice() {
        return itemService.findAllItemsWithPrice();
    }
}
