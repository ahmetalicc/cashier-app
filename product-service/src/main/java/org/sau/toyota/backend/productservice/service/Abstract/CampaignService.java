package org.sau.toyota.backend.productservice.service.Abstract;


import org.sau.toyota.backend.productservice.dto.response.CampaignResponse;

import java.util.List;

public interface CampaignService {
    List<CampaignResponse> getAllCampaigns(int page, int size, String sortBy, String sortOrder, String filter);


    CampaignResponse getOneCampaign(Long id);
}
