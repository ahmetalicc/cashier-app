package org.sau.Toyota.Backend.kasiyerapp.Dao;

import org.sau.Toyota.Backend.kasiyerapp.Entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Campaign findCampaignByCategoryId(Long id);
}
