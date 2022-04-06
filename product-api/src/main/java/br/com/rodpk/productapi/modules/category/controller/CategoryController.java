package br.com.rodpk.productapi.modules.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodpk.productapi.modules.category.dto.CategoryRequest;
import br.com.rodpk.productapi.modules.category.dto.CategoryResponse;
import br.com.rodpk.productapi.modules.category.service.CategoryService;

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
