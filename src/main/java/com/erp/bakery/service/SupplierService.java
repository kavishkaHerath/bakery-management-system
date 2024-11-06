package com.erp.bakery.service;

import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.model.Supplier;
import com.erp.bakery.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier updateSupplier(Supplier updatedSupplier) {
        var supplierID = updatedSupplier.getSupplierCode();
        var phone = updatedSupplier.getPhone();
        var email = updatedSupplier.getEmail();

        Supplier existingSupplier = supplierRepository.findById(supplierID).
                orElseThrow(() -> new RuntimeException("Supplier not found"));

        // Check if email is in use by another supplier
        if (email != null && !email.equals(existingSupplier.getEmail()) &&
                supplierRepository.existsByEmail(email)) {
            throw new DuplicateFieldException("Email " + email + " is already in use.");
        }
        // Check if phone number is in use by another employee
        if (phone != null && !phone.equals(existingSupplier.getPhone()) &&
                supplierRepository.existsByPhone(phone)) {
            throw new DuplicateFieldException("Phone number " + phone + " is already in use.");
        }

        // Update allowed fields
        existingSupplier.setSupplierName(updatedSupplier.getSupplierName());
        existingSupplier.setCompaneyName(updatedSupplier.getCompaneyName());
        existingSupplier.setEmail(email);
        existingSupplier.setPhone(phone);
        existingSupplier.setAddress(updatedSupplier.getAddress());
        existingSupplier.setImageUrl(updatedSupplier.getImageUrl());

        return supplierRepository.save(existingSupplier);
    }
}
