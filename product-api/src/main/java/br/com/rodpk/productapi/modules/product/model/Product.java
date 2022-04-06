package br.com.rodpk.productapi.modules.product.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT")

public class Product {


    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;


    @ManyToOne
    @JoinColumn(name = "FK_CATEGORY", nullable = false)
    private Category category;
    

    @ManyToOne
    @JoinColumn(name = "FK_SUPPLIER", nullable = false)
    private Supplier supplier;

    @Column(name = "QUANTITY_AVAIABLE", nullable = false)
    private Integer quantityAvaiable;
    
}
