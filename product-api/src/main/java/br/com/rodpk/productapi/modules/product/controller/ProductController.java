package br.com.rodpk.productapi.modules.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodpk.productapi.config.exception.SuccessResponse;
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

    @GetMapping("/name/{name}")
    public List<ProductResponse> findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> findByCategoryId(@PathVariable Integer categoryId) {
        return service.findByCategoryId(categoryId);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Integer id, @RequestBody ProductRequest request) {
        return service.update(request, id);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<ProductResponse> findBySupplierId(@PathVariable Integer supplierId) {
        return service.findBySupplierId(supplierId);
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Integer id) {
        return service.findByIdResponse(id);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse delete(@PathVariable Integer id) {
        return service.delete(id);
    }

}
