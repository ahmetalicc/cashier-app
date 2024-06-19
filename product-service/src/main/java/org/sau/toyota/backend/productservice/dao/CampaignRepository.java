package org.sau.toyota.backend.productservice.dao;

import org.sau.toyota.backend.productservice.entity.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Repository interface that extends JpaRepository for {@link Campaign} entity.
 */
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    /**
     * Finds a campaign by its category ID.
     *
     * @param id Category ID
     * @return The campaign associated with the given category ID
     */
    Campaign findCampaignByCategoryId(Long id);
    /**
     * Finds campaigns whose name or description contains the specified filter, ignoring case sensitivity.
     *
     * @param filter The filter to apply to campaign names and descriptions
     * @param pageable Pageable object to control pagination
     * @return A page of campaigns matching the filter
     */
    @Query("SELECT c FROM Campaign c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :filter, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Campaign> findCampaignsByNameOrDescriptionContains(String filter, Pageable pageable);
}
