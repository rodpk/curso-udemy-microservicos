package br.com.rodpk.productapi.modules.supplier.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodpk.productapi.config.exception.ValidationException;
import br.com.rodpk.productapi.modules.supplier.dto.SupplierRequest;
import br.com.rodpk.productapi.modules.supplier.dto.SupplierResponse;
import br.com.rodpk.productapi.modules.supplier.model.Supplier;
import br.com.rodpk.productapi.modules.supplier.repository.SupplierRepository;

@Service
public class SupplierService {
    
    @Autowired // faz injeção de dependência
    private SupplierRepository repository;


    public Supplier findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ValidationException("Supplier not found"));
    }

    public SupplierResponse findByIdResponse(Integer id) {
        if(id == null) throw new ValidationException("id must be informed");
        return SupplierResponse.of(findById(id));
    }
    
    public SupplierResponse save(SupplierRequest request) {
        validateSupplierNameInformed(request);
        var supplier = repository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }
    public List<SupplierResponse> findByName(String name) {
        if (name.isEmpty())
            throw new ValidationException("category description must be informed.");

        return repository.findByNameIgnoreCase(name).stream().map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findAll() {
        return repository.findAll().stream().map(SupplierResponse::of).collect(Collectors.toList());
    }

    private void validateSupplierNameInformed(SupplierRequest request) {
        if(request.getName() == null) {
            throw new ValidationException("Category name is required");
        }
    }
}
