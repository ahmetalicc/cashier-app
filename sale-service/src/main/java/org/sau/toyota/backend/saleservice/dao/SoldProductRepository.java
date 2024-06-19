package org.sau.toyota.backend.saleservice.dao;

import org.sau.toyota.backend.saleservice.entity.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;


/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Repository interface for managing SoldProduct entities.
 * This interface extends JpaRepository to provide basic CRUD operations for SoldProduct entities.
 */
public interface SoldProductRepository extends JpaRepository<SoldProduct, Long> {
}
