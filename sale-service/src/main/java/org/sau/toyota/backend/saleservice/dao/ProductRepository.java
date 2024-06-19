package org.sau.toyota.backend.saleservice.dao;

import org.sau.toyota.backend.saleservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Repository interface that extends JpaRepository for {@link Product} entity.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds all products belonging to a specific category by category ID.
     *
     * @param id The ID of the category to filter products
     * @param pageable Pageable object to control pagination
     * @return A page of products belonging to the specified category
     */
    Page<Product> findAllProductsByCategoryId(Long id, Pageable pageable);
    /**
     * Finds products whose name or description contains the specified filter, ignoring case sensitivity.
     *
     * @param filter The filter to apply to product names and descriptions
     * @param pageable Pageable object to control pagination
     * @return A page of products matching the filter
     */
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :filter, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Product> findProductsByNameOrDescriptionContains(@Param("filter") String filter, Pageable pageable);

}
