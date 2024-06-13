package org.sau.toyota.backend.saleservice.dao;

import org.sau.toyota.backend.saleservice.entity.SoldProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoldProductRepository extends JpaRepository<SoldProduct, Long> {
}
