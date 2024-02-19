package org.sau.Toyota.Backend.kasiyerapp.Dao;

import org.sau.Toyota.Backend.kasiyerapp.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllProductsByCategoryId(Long id, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :filter, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Product> findProductsByNameOrDescriptionContains(@Param("filter") String filter, Pageable pageable);

}
