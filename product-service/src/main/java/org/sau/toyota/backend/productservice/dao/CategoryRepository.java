package org.sau.toyota.backend.productservice.dao;

import org.sau.toyota.backend.productservice.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :filter, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Category> findCategoriesByNameOrDescriptionContains(@Param("filter") String filter, Pageable pageable);
}
