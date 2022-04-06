package br.com.rodpk.productapi.modules.supplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodpk.productapi.modules.supplier.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
    
}
