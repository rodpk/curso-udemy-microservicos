package br.com.rodpk.productapi.modules.category.service;

import java.util.List;
import java.util.stream.Collectors;

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
        return repository.findById(id).orElseThrow(() -> new ValidationException("Category not found"));
    }

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryNameInformed(request);
        var category = repository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    public CategoryResponse findByIdResponse(Integer id) {
        if(id == null) throw new ValidationException("id must be informed");
        return CategoryResponse.of(findById(id));
    }

    public List<CategoryResponse> findAll() {
        return repository.findAll().stream().map(CategoryResponse::of).collect(Collectors.toList());
    }

    public List<CategoryResponse> findByDescription(String description) {
        if (description.isEmpty())
            throw new ValidationException("category description must be informed.");
        // pra cada category mapeia pra categoryResponse
        // .map(category -> CategoryResponse.of(category)) == CategoryResponse::of
        //--
        return repository.findByDescriptionContainingIgnoreCase(description).stream().map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    private void validateCategoryNameInformed(CategoryRequest request) {
        if (request.getDescription() == null) {
            throw new ValidationException("Category name is required");
        }
    }
}
