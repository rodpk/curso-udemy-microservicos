package br.com.rodpk.productapi.modules.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodpk.productapi.modules.category.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // ignoreCaseContaining = sql vai ignorar o case e vai colocar os %%
    // ta rodando uma vez mas depois crasha TODO
    List<Category> findByDescriptionIgnoreCase(String description);
}
