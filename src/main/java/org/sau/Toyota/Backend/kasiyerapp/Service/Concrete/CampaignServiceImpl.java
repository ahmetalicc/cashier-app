package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dao.CampaignRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CampaignResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Campaign;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.CampaignService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;

    @Override
    public List<CampaignResponse> getAllCampaigns(int page, int size, String sortBy, String sortOrder, String filter) {
        Sort sort = Sort.by(sortBy).ascending();
        if ("desc".equals(sortOrder)) {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Campaign> campaigns;
        if(filter != null && !filter.isEmpty()){
            campaigns = campaignRepository.findCampaignsByNameOrDescriptionContains(filter, pageable);
        }else {
            campaigns = campaignRepository.findAll(pageable);
        }
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
