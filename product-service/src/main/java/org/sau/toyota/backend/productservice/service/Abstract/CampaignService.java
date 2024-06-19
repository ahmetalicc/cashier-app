package org.sau.toyota.backend.productservice.service.Abstract;


import org.sau.toyota.backend.productservice.dto.response.CampaignResponse;

import java.util.List;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * This service interface provides methods to manage campaigns.
 * It defines operations to retrieve all campaigns or get a single campaign by ID.
 */
public interface CampaignService {
    /**
     * Retrieves a list of all campaigns based on pagination and filtering options.
     *
     * @param page      Page number
     * @param size      Number of items per page
     * @param sortBy    Field to sort by
     * @param sortOrder Sort order (asc for ascending, desc for descending)
     * @param filter    Filter criteria to apply
     * @return List of CampaignResponse objects representing campaigns
     */
    List<CampaignResponse> getAllCampaigns(int page, int size, String sortBy, String sortOrder, String filter);

    /**
     * Retrieves a single campaign by its ID.
     *
     * @param id Campaign ID
     * @return CampaignResponse object representing the campaign
     */
    CampaignResponse getOneCampaign(Long id);
}
