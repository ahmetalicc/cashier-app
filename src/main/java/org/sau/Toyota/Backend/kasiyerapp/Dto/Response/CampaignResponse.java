package org.sau.Toyota.Backend.kasiyerapp.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Campaign;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignResponse {

    private String name;

    private String description;

    private String category;

    public static CampaignResponse Convert(Campaign campaign){
        return CampaignResponse.builder()
                .name(campaign.getName())
                .description(campaign.getDescription())
                .category(campaign.getCategory().getName())
                .build();
    }
}
