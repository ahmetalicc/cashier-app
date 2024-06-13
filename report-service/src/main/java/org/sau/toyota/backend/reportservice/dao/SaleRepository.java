package org.sau.toyota.backend.reportservice.dao;

import org.sau.toyota.backend.reportservice.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


public interface SaleRepository extends JpaRepository<Sale, Long> {

    Page<Sale> findSalesBySaleTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
