package org.sau.toyota.backend.productservice.api;


import lombok.RequiredArgsConstructor;
import org.sau.toyota.backend.productservice.core.results.DataResult;
import org.sau.toyota.backend.productservice.core.results.ErrorDataResult;
import org.sau.toyota.backend.productservice.core.results.SuccessDataResult;
import org.sau.toyota.backend.productservice.dto.response.CampaignResponse;
import org.sau.toyota.backend.productservice.service.Abstract.CampaignService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaign")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @GetMapping("/getAllCampaigns")
    public DataResult<List<CampaignResponse>> getAllCampaigns(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "3") int size,
                                                              @RequestParam(defaultValue = "id") String sortBy,
                                                              @RequestParam(defaultValue = "asc") String sortOrder,
                                                              @RequestParam(required = false) String filter){
        return new SuccessDataResult<>
                (campaignService.getAllCampaigns(page, size, sortBy, sortOrder, filter), "Data has been listed.");
    }

    @GetMapping("/getOneCampaign/{id}")
    public DataResult<CampaignResponse> getOneCampaign(@PathVariable("id") Long id){
        try {
            return new SuccessDataResult<>(campaignService.getOneCampaign(id), "Data has been listed.");
        }
        catch (NullPointerException e){
            return new ErrorDataResult<>(e.getMessage());
        }
    }
}
