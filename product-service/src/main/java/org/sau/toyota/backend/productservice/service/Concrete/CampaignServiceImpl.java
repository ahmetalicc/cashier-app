package org.sau.toyota.backend.productservice.service.Concrete;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
            log.debug("Filtering campaigns with filter: {}", filter);
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
                () -> {
                    log.warn("Campaign not found with id: {}", id);
                    return new NullPointerException(String.format("Campaign not found with id: %s", id));
                });
        return CampaignResponse.Convert(campaign);
    }
}
