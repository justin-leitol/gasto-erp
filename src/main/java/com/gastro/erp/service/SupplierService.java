package com.gastro.erp.service;

import com.gastro.erp.exception.ResourceNotFoundException;
import com.gastro.erp.model.Supplier;
import com.gastro.erp.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier supplier) {
        Supplier existing = getSupplierById(id);
        existing.setName(supplier.getName());
        existing.setAddress(supplier.getAddress());
        existing.setPhone(supplier.getPhone());
        existing.setEmail(supplier.getEmail());
        existing.setNotes(supplier.getNotes());
        return supplierRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
    }

    @Transactional(readOnly = true)
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
