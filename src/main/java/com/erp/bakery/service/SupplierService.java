package com.erp.bakery.service;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.model.Supplier;
import com.erp.bakery.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier addSupplier(Supplier supplier) {
        // Check for duplicate email and phone
        if (supplierRepository.existsByEmail(supplier.getEmail())) {
            throw new DuplicateFieldException("Email " + supplier.getEmail() + " is already in use.");
        }
        if (supplierRepository.existsByPhone(supplier.getPhone())) {
            throw new DuplicateFieldException("Phone number " + supplier.getPhone() + " is already in use.");
        }
        return supplierRepository.save(supplier);
    }
}
