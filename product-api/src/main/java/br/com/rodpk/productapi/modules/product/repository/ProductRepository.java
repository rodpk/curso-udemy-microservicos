package br.com.rodpk.productapi.modules.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodpk.productapi.modules.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
 
    
    public List<Product> findByNameIgnoreCase(String name);
    public List<Product> findByCategoryId(Integer id);
    public List<Product> findBySupplierId(Integer id);

    Boolean existsByCategoryId(Integer id);
    Boolean existsBySupplierId(Integer id);

}
