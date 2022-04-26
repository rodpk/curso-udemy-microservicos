package br.com.rodpk.productapi.modules.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodpk.productapi.config.exception.SuccessResponse;
import br.com.rodpk.productapi.config.exception.ValidationException;
import br.com.rodpk.productapi.modules.category.service.CategoryService;
import br.com.rodpk.productapi.modules.product.dto.ProductQuantityDTO;
import br.com.rodpk.productapi.modules.product.dto.ProductRequest;
import br.com.rodpk.productapi.modules.product.dto.ProductResponse;
import br.com.rodpk.productapi.modules.product.dto.ProductSalesResponse;
import br.com.rodpk.productapi.modules.product.dto.ProductStockDTO;
import br.com.rodpk.productapi.modules.product.model.Product;
import br.com.rodpk.productapi.modules.product.repository.ProductRepository;
import br.com.rodpk.productapi.modules.sales.client.SalesClient;
import br.com.rodpk.productapi.modules.sales.dto.SalesConfirmationDTO;
import br.com.rodpk.productapi.modules.sales.enums.SalesStatus;
import br.com.rodpk.productapi.modules.sales.rabbitmq.SalesConfirmationSender;
import br.com.rodpk.productapi.modules.supplier.service.SupplierService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

    private static final Integer ZERO = 0;

    @Autowired // faz injeção de dependência
    private ProductRepository repository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SalesClient salesClient;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SalesConfirmationSender sender;

    public ProductResponse save(ProductRequest request) {
        validateProductData(request);
        // ja tem foreign key...
        var category = categoryService.findById(request.getCategoryID());
        var supplier = supplierService.findById(request.getSupplierID());
        var product = repository.save(Product.of(request, category, supplier));
        return ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request, Integer id) {
        validateProductData(request);
        // ja tem foreign key...
        var category = categoryService.findById(request.getCategoryID());
        var supplier = supplierService.findById(request.getSupplierID());
        var product = Product.of(request, category, supplier);
        product.setId(id);
        repository.save(product);
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


    public SuccessResponse delete(Integer id) {
        if (id == null)
            throw new ValidationException("id must be informed");

        repository.deleteById(id);
        return SuccessResponse.create("Product deleted");
    }
    public Boolean existsByCategoryId(Integer categoryId) {
        return repository.existsByCategoryId(categoryId);
    }

    public Boolean existsBySupplierId(Integer categoryId) {
        return repository.existsBySupplierId(categoryId);
    }

    public void updateProductStock(ProductStockDTO dto) {
        try {
            validateStockUpdateData(dto);
            updateStock(dto);
        } catch(Exception ex) {
            log.error("error while trying to update stock for message with error: {}", ex.getMessage(), ex);
            var rejected = new SalesConfirmationDTO(dto.getSalesId(), SalesStatus.REJECTED);
            sender.sendSalesConfirmationMessage(rejected);
        }
    }

    @Transactional
    private void validateStockUpdateData(ProductStockDTO dto) {
        if (dto == null || dto.getSalesId() == null) throw new ValidationException("product data or id must be informed.");
        

        if (dto.getProducts().isEmpty()) throw new ValidationException("sales products must be informed.");

        dto.getProducts().forEach(salesProduct -> {
            if (salesProduct.getProductId() == null || salesProduct.getQuantity() == null) 
                throw new ValidationException("product id and quantity must be informed");
        });
    }

    private void updateStock(ProductStockDTO dto) {

        var productsForUpdate = new ArrayList<Product>();
        

        dto.getProducts().forEach(salesProduct -> {
            var existingProduct = findById(salesProduct.getProductId());
            validateQuantityInStock(salesProduct, existingProduct);

            existingProduct.updateStock(salesProduct.getQuantity());
            productsForUpdate.add(existingProduct);
        });

        if (!productsForUpdate.isEmpty()) {
            repository.saveAll(productsForUpdate);
            var approvedMessage = new SalesConfirmationDTO(dto.getSalesId(), SalesStatus.APPROVED);
            sender.sendSalesConfirmationMessage(approvedMessage);
        }

    }

    private void validateQuantityInStock(ProductQuantityDTO salesProduct, Product existingProduct) {
        if (salesProduct.getQuantity() > existingProduct.getQuantityAvaiable()){
            throw new ValidationException(String.format("the product $s is out of stock", existingProduct.getName()));
        }
    }

    public ProductSalesResponse findProductSales(Integer id) {
        var product = findById(id);
        try {
            var sales = salesClient.findSalesByProductId(id).orElseThrow(() -> new ValidationException("sales was not found to this product."));
            return ProductSalesResponse.of(product, sales.getSalesIds());
        } catch(Exception ex) {
            throw new ValidationException("error trying to get the product's sales");
        }
    }
}
