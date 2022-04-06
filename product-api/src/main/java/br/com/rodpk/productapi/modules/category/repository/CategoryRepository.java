package br.com.rodpk.productapi.modules.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodpk.productapi.modules.category.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
