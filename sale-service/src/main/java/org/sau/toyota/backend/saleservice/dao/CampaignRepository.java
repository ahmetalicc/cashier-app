package org.sau.toyota.backend.saleservice.dao;

import org.sau.toyota.backend.saleservice.entity.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Campaign findCampaignByCategoryId(Long id);

    @Query("SELECT c FROM Campaign c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :filter, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Campaign> findCampaignsByNameOrDescriptionContains(String filter, Pageable pageable);
}
