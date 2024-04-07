package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.DataResult;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.ErrorDataResult;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.SuccessDataResult;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CampaignResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CategoryResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.CampaignService;
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
