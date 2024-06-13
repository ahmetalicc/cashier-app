package org.sau.toyota.backend.saleservice.dao;

import org.sau.toyota.backend.saleservice.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface SaleRepository extends JpaRepository<Sale, Long> {

    Page<Sale> findSalesBySaleTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
