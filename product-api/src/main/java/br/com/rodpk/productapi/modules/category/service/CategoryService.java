package br.com.rodpk.productapi.modules.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodpk.productapi.config.exception.ValidationException;
import br.com.rodpk.productapi.modules.product.dto.CategoryRequest;
import br.com.rodpk.productapi.modules.product.dto.CategoryResponse;
import br.com.rodpk.productapi.modules.product.model.Category;
import br.com.rodpk.productapi.modules.product.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired // faz injeção de dependência
    private CategoryRepository repository;

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryNameInformed(request);
        var category = repository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    private void validateCategoryNameInformed(CategoryRequest request) {
        if(request.getDescription() == null) {
            throw new ValidationException("Category name is required");
        }
    }
}
