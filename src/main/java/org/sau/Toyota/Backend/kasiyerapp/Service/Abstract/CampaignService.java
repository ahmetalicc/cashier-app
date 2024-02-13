package org.sau.Toyota.Backend.kasiyerapp.Service.Abstract;

import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CampaignResponse;

import java.util.List;

public interface CampaignService {
    List<CampaignResponse> getAllCampaigns();


    CampaignResponse getOneCampaign(Long id);
}
