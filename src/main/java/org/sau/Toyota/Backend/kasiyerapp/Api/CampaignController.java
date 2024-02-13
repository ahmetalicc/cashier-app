package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CampaignResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CategoryResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.CampaignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/campaign")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @GetMapping("/getAllCampaigns")
    public List<CampaignResponse> getAllCampaigns(){
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/getOneCampaign/{id}")
    public CampaignResponse getOneCampaign(@PathVariable("id") Long id){
        return campaignService.getOneCampaign(id);
    }
}
