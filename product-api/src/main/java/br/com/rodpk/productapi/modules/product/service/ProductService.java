package br.com.rodpk.productapi.modules.product.service;

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
