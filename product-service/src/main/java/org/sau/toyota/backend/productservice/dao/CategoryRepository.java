package org.sau.toyota.backend.productservice.dao;

import org.sau.toyota.backend.productservice.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Repository interface that extends JpaRepository for {@link Category} entity.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Finds categories whose name or description contains the specified filter, ignoring case sensitivity.
     *
     * @param filter The filter to apply to category names and descriptions
     * @param pageable Pageable object to control pagination
     * @return A page of categories matching the filter
     */
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :filter, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Category> findCategoriesByNameOrDescriptionContains(@Param("filter") String filter, Pageable pageable);
}
