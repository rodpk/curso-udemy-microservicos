package br.com.rodpk.productapi.modules.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodpk.productapi.modules.product.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
    
}
