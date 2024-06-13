package org.sau.toyota.backend.productservice.service.Concrete;

import lombok.RequiredArgsConstructor;
import org.sau.toyota.backend.productservice.dao.CampaignRepository;
import org.sau.toyota.backend.productservice.dto.response.CampaignResponse;
import org.sau.toyota.backend.productservice.entity.Campaign;
import org.sau.toyota.backend.productservice.service.Abstract.CampaignService;
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
        Campaign campaign = campaignRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("Campaign not found with id: %s", id)));
        return CampaignResponse.Convert(campaign);
    }
}
