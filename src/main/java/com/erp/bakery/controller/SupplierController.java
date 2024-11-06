package com.erp.bakery.controller;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.model.Supplier;
import com.erp.bakery.response.ResponseMessage;
import com.erp.bakery.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

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
}
