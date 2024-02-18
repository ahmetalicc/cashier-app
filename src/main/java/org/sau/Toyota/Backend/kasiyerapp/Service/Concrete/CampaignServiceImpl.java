package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dao.CampaignRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CampaignResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Campaign;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.CampaignService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    @Override
    public List<CampaignResponse> getAllCampaigns() {
        List<Campaign> campaigns = campaignRepository.findAll();

        return campaigns.stream()
                .map(CampaignResponse::Convert)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignResponse getOneCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow();
        return CampaignResponse.Convert(campaign);
    }
}
