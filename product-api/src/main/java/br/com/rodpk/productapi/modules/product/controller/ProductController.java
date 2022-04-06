package br.com.rodpk.productapi.modules.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodpk.productapi.modules.product.dto.ProductRequest;
import br.com.rodpk.productapi.modules.product.dto.ProductResponse;
import br.com.rodpk.productapi.modules.product.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request) {
        return service.save(request);
    }
    
}
