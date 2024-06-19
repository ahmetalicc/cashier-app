package org.sau.toyota.backend.reportservice.dao;

import org.sau.toyota.backend.reportservice.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
/** @author Ahmet Alıç
 * @since 15-06-2024
 * <p>
 * The primary purpose of this repository is to manage Sale entities in the database and provide methods to query sales data
 * based on different criteria, such as time range. It also supports pagination and sorting of query results.
 * </p>
 *
 * @see JpaRepository
 * @see Sale
 */

public interface SaleRepository extends JpaRepository<Sale, Long> {

    /**
     * Finds sales that occurred within a specified time range, with support for pagination and sorting.
     *
     * <p>
     * This method retrieves a page of Sale entities that have their sale time between the given start and end
     * LocalDateTime values. The results can be paginated and sorted based on the Pageable parameter.
     * </p>
     *
     * @param startDateTime the start of the time range for which sales are to be retrieved (inclusive)
     * @param endDateTime the end of the time range for which sales are to be retrieved (inclusive)
     * @param pageable an object specifying the pagination and sorting information
     * @return a Page of Sale entities that occurred within the specified time range
     *
     * @see Page
     * @see Pageable
     * @see LocalDateTime
     */
    Page<Sale> findSalesBySaleTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
