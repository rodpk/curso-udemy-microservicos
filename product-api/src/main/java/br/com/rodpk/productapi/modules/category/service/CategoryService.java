package br.com.rodpk.productapi.modules.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodpk.productapi.config.exception.ValidationException;
import br.com.rodpk.productapi.modules.category.dto.CategoryRequest;
import br.com.rodpk.productapi.modules.category.dto.CategoryResponse;
import br.com.rodpk.productapi.modules.category.model.Category;
import br.com.rodpk.productapi.modules.category.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired // faz injeção de dependência
    private CategoryRepository repository;




    public Category findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ValidationException("Supplier not found"));
    }
    

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
