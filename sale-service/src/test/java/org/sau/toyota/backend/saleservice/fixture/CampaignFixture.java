package org.sau.toyota.backend.saleservice.fixture;


import org.sau.toyota.backend.saleservice.entity.Campaign;

import java.util.ArrayList;
import java.util.List;
/** @author Ahmet Alıç
 * @since 15-06-2024
 * Fixture class for generating Campaign objects for testing purposes.
 */
public class CampaignFixture extends Fixture<Campaign>{

    CategoryFixture categoryFixture = new CategoryFixture();
    /**
     * Creates a list of Campaign objects with randomly generated fake data.
     *
     * @return List of Campaign objects
     */
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
    /**
     * Creates a single Campaign object with randomly generated fake data.
     *
     * @return Campaign object
     */
    public Campaign createCampaign(){
        Campaign campaign = new Campaign();
        campaign.setId(faker.number().randomNumber());
        campaign.setName(faker.gameOfThrones().character());
        campaign.setDescription(faker.lorem().sentence());
        campaign.setCategory(categoryFixture.createCategory());

        return campaign;
    }
}
