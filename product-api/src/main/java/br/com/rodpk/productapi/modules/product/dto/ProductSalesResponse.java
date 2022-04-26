package br.com.rodpk.productapi.modules.product.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.rodpk.productapi.modules.category.dto.CategoryResponse;
import br.com.rodpk.productapi.modules.product.model.Product;
import br.com.rodpk.productapi.modules.supplier.dto.SupplierResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesResponse {
    

    private Integer id;
    private String name;
    private Integer quantityAvaiable;
    private List<String> sales;
    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private SupplierResponse supplier;
    private CategoryResponse category;


    public static ProductSalesResponse of(Product product, List<String> sales) {
        return ProductSalesResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantityAvaiable(product.getQuantityAvaiable())
                .createdAt(product.getCreatedAt())
                .supplier(SupplierResponse.of(product.getSupplier()))
                .category(CategoryResponse.of(product.getCategory()))
                .sales(sales)
                .build();
    }
}
