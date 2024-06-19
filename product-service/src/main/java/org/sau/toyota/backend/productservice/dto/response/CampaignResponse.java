package org.sau.toyota.backend.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.productservice.entity.Campaign;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a response DTO (Data Transfer Object) for a campaign.
 * Contains information such as the name, description, and category of the campaign.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignResponse {

    /**
     * The name of the campaign.
     */
    private String name;

    /**
     * The description of the campaign.
     */
    private String description;

    /**
     * The category of the campaign.
     */
    private String category;

    /**
     * Converts a Campaign entity to a CampaignResponse object.
     *
     * @param campaign The Campaign entity to convert
     * @return A CampaignResponse object converted from the Campaign entity
     */
    public static CampaignResponse Convert(Campaign campaign){
        return CampaignResponse.builder()
                .name(campaign.getName())
                .description(campaign.getDescription())
                .category(campaign.getCategory().getName())
                .build();
    }
}
