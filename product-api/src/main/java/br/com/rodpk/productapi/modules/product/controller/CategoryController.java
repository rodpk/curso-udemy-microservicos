package br.com.rodpk.productapi.modules.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodpk.productapi.modules.product.dto.CategoryRequest;
import br.com.rodpk.productapi.modules.product.dto.CategoryResponse;
import br.com.rodpk.productapi.modules.product.service.CategoryService;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    
    @Autowired
    private CategoryService service;

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest request) {
        return service.save(request);
    }
}
