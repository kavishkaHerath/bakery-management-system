package com.erp.bakery.service;

import com.erp.bakery.exception.DeletionException;
import com.erp.bakery.exception.DuplicateFieldException;
import com.erp.bakery.exception.NotFoundException;
import com.erp.bakery.model.Supplier;
import com.erp.bakery.model.SupplierDTO;
import com.erp.bakery.model.dto.SupplierGetDTO;
import com.erp.bakery.repository.ItemRepository;
import com.erp.bakery.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private final SupplierRepository supplierRepository;
    @Autowired
    private final ItemRepository itemRepository;

    public SupplierService(SupplierRepository supplierRepository, ItemRepository itemRepository) {
        this.supplierRepository = supplierRepository;
        this.itemRepository = itemRepository;
    }

    public Supplier addSupplier(Supplier supplier) {
        // Check for duplicate email and phone
        if (supplierRepository.existsByEmail(supplier.getEmail())) {
            throw new DuplicateFieldException("Email " + supplier.getEmail() + " is already in use.");
        }
        if (supplierRepository.existsByPhone(supplier.getPhone())) {
            throw new DuplicateFieldException("Phone number " + supplier.getPhone() + " is already in use.");
        }
        supplier.setAddDate(LocalDate.now());
        return supplierRepository.save(supplier);
    }

    public List<SupplierGetDTO> getAllSuppliers() {
        return supplierRepository.findAllSupplierDetails();
    }

    public Supplier getSupplierByCode(Long supplierCode) {
        return supplierRepository.findById(supplierCode).orElseThrow(
                () -> new NotFoundException("Supplier not found with Supplier Code: " + supplierCode)
        );
    }

    public List<SupplierDTO> getActiveSupplier() {
        return supplierRepository.findActiveSupplier();
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
        existingSupplier.setModifyDate(LocalDate.now());
        existingSupplier.setModifiedEmployee(updatedSupplier.getModifiedEmployee());

        return supplierRepository.save(existingSupplier);
    }

    public void deleteSupplierDetails(Long supplierCode) {
        if (!supplierRepository.existsById(supplierCode)) {
            throw new NotFoundException("Supplier not found with Employee code: " + supplierCode);
        }
        // Check if the category is referenced in the Item table
        boolean isReferenced = itemRepository.existsBySupplier_SupplierCode(supplierCode);
        if (isReferenced) {
            throw new DeletionException(
                    "Supplier with Supplier Code :" + supplierCode + " is associated with items and cannot be deleted."
            );
        }
        supplierRepository.deleteById(supplierCode);
    }

}
