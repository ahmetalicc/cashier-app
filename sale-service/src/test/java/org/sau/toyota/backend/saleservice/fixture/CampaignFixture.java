package org.sau.toyota.backend.saleservice.fixture;


import org.sau.toyota.backend.saleservice.entity.Campaign;

import java.util.ArrayList;
import java.util.List;

public class CampaignFixture extends Fixture<Campaign>{

    CategoryFixture categoryFixture = new CategoryFixture();

    public List<Campaign> createCampaignList(){
        List<Campaign> campaigns = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Campaign campaign = Campaign.builder()
                    .id(faker.number().randomNumber())
                    .name(faker.company().name())
                    .description(faker.lorem().sentence())
                    .category(categoryFixture.createCategory())
                    .build();
            campaigns.add(campaign);
        }

        return campaigns;
    }

    public Campaign createCampaign(){
        Campaign campaign = new Campaign();
        campaign.setId(faker.number().randomNumber());
        campaign.setName(faker.gameOfThrones().character());
        campaign.setDescription(faker.lorem().sentence());
        campaign.setCategory(categoryFixture.createCategory());

        return campaign;
    }
}
