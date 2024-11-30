package com.erp.bakery.controller;

import com.erp.bakery.exception.DeletionException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.Supplier;
import com.erp.bakery.model.SupplierDTO;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin("*")
public class SupplierController {
    @Autowired
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping("/supplier-register")
    public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier) {
        try {
            Supplier newSupplier = supplierService.addSupplier(supplier);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSupplier);
        } catch (DuplicateFieldException ex) {
            ResponseMessage responseMessage = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            ResponseMessage errorResponse = new ResponseMessage(
                    "error",
                    "An unexpected error occurred.",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/get-all-supplier")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("getSupplier/active")
    public ResponseEntity<List<SupplierDTO>> getActiveCategories() {
        List<SupplierDTO> activeSupplier = supplierService.getActiveSupplier();
        return ResponseEntity.ok(activeSupplier);
    }

    @GetMapping("getSupplier/{supplierCode}")
    public ResponseEntity<?> getSupplierByCode(@PathVariable Long supplierCode) {
        try {
            Supplier supplier = supplierService.getSupplierByCode(supplierCode);
            return ResponseEntity.ok(supplier);
        } catch (NotFoundException ex) {
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }

    @PutMapping("/editSupplierDetails")
    public ResponseEntity<?> updateSupplierDetails(@RequestBody Supplier updatedSupplier) {
        try {
            Supplier supplier = supplierService.updateSupplier(updatedSupplier);

            ResponseMessage response = new ResponseMessage(
                    "success",
                    "Supplier updated successfully",
                    String.valueOf(updatedSupplier.getSupplierCode())
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DuplicateFieldException ex) {
            ResponseMessage duplicateError = new ResponseMessage(
                    "error-duplicate",
                    ex.getMessage(),
                    String.valueOf(updatedSupplier.getSupplierCode())
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(duplicateError);
        } catch (Exception ex) {
            ResponseMessage error = new ResponseMessage(
                    "error",
                    "Failed to update employee: " + ex.getMessage(),
                    null
            );
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    // Method to delete employee by ID
    @DeleteMapping("/delete/{supplierCode}")
    public ResponseEntity<?> deleteSupplierDetails(@PathVariable Long supplierCode) {
        try {
            supplierService.deleteSupplierDetails(supplierCode); // Call service to delete the employee
            return ResponseEntity.status(HttpStatus.OK).body("Supplier with Supplier Code " + supplierCode + " deleted successfully.");
        } catch (NotFoundException ex) {
            // Handle employee not found
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    null  // No employee code for error response
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        } catch (DeletionException ex) {
            ResponseMessage responseMessage = new ResponseMessage(
                    "error",
                    ex.getMessage(),
                    supplierCode.toString()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }
}
