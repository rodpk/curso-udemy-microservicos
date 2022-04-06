package br.com.rodpk.productapi.modules.supplier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodpk.productapi.modules.supplier.dto.SupplierRequest;
import br.com.rodpk.productapi.modules.supplier.dto.SupplierResponse;
import br.com.rodpk.productapi.modules.supplier.service.SupplierService;


@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService service;

    @PostMapping
    public SupplierResponse save(@RequestBody SupplierRequest request) {
        return service.save(request);
    }
    
}
