package br.com.rodpk.productapi.modules.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodpk.productapi.config.exception.ValidationException;
import br.com.rodpk.productapi.modules.category.service.CategoryService;
import br.com.rodpk.productapi.modules.product.dto.ProductRequest;
import br.com.rodpk.productapi.modules.product.dto.ProductResponse;
import br.com.rodpk.productapi.modules.product.model.Product;
import br.com.rodpk.productapi.modules.product.repository.ProductRepository;
import br.com.rodpk.productapi.modules.supplier.service.SupplierService;

@Service
public class ProductService {

    private static final Integer ZERO = 0;

    @Autowired // faz injeção de dependência
    private ProductRepository repository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryService categoryService;

    public ProductResponse save(ProductRequest request) {
        validateProductData(request);
        // ja tem foreign key...
        var category = categoryService.findById(request.getCategoryID());
        var supplier = supplierService.findById(request.getSupplierID());
        var product = repository.save(Product.of(request, category, supplier));
        return ProductResponse.of(product);
    }

    public Product findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ValidationException("Category not found"));
    }

    public ProductResponse findByIdResponse(Integer id) {
        if(id == null) throw new ValidationException("id must be informed");
        return ProductResponse.of(findById(id));
    }
    public List<ProductResponse> findByName(String name) {
        if (name.isEmpty())
            throw new ValidationException("category description must be informed.");

        return repository.findByNameIgnoreCase(name).stream().map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(ProductResponse::of).collect(Collectors.toList());
    }


    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        if (supplierId == null)
            throw new ValidationException("supplier id must be informed");

        return repository.findBySupplierId(supplierId).stream().map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        if (categoryId == null)
            throw new ValidationException("supplier id must be informed");

        return repository.findBySupplierId(categoryId).stream().map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    private void validateProductData(ProductRequest request) {
        if (request.getName() == null) {
            throw new ValidationException("Category name is required");
        }

        if (request.getQuantityAvaiable() == null || request.getQuantityAvaiable() <= ZERO) {
            throw new ValidationException("Quantity avaiable is required and should not be less or equal to zero.");
        }

        if (request.getSupplierID() == null) {
            throw new ValidationException("Supplier id is required");
        }

        if (request.getCategoryID() == null) {
            throw new ValidationException("Category id is required");
        }
    }
}
