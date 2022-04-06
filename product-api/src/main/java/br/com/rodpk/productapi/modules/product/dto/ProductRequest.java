package br.com.rodpk.productapi.modules.product.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private Integer quantityAvaiable;
    private Integer supplierID;
    private Integer categoryID;
}
