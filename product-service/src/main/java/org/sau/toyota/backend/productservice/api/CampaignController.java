package org.sau.toyota.backend.productservice.api;


import lombok.RequiredArgsConstructor;
import org.sau.toyota.backend.productservice.core.results.DataResult;
import org.sau.toyota.backend.productservice.core.results.ErrorDataResult;
import org.sau.toyota.backend.productservice.core.results.SuccessDataResult;
import org.sau.toyota.backend.productservice.dto.response.CampaignResponse;
import org.sau.toyota.backend.productservice.service.Abstract.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * REST controller for managing campaigns.
 * <p>
 * Provides endpoints to retrieve campaign data.
 */
@RestController
@RequestMapping("/campaign")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;
    /**
     * Retrieves a list of campaigns with pagination and sorting options.
     *
     * @param page      Page number to retrieve
     * @param size      Number of items per page
     * @param sortBy    Field to sort by
     * @param sortOrder Sort order, can be 'asc' for ascending or 'desc' for descending
     * @param filter    Optional filter parameter
     * @return DataResult containing a list of CampaignResponse objects
     */
    @GetMapping("/getAllCampaigns")
    public ResponseEntity<DataResult<List<CampaignResponse>>> getAllCampaigns(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "3") int size,
                                                                             @RequestParam(defaultValue = "id") String sortBy,
                                                                             @RequestParam(defaultValue = "asc") String sortOrder,
                                                                             @RequestParam(required = false) String filter){
        return ResponseEntity.ok(new SuccessDataResult<>
                (campaignService.getAllCampaigns(page, size, sortBy, sortOrder, filter), "Data has been listed successfully."));
    }

    /**
     * Retrieves a single campaign by its ID.
     *
     * @param id The ID of the campaign to retrieve.
     * @return A {@link DataResult} containing a {@link CampaignResponse} object if the campaign is found,
     * or an {@link ErrorDataResult} if the campaign is not found or an exception occurs.
     */
    @GetMapping("/getOneCampaign/{id}")
    public ResponseEntity<DataResult<CampaignResponse>> getOneCampaign(@PathVariable("id") Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessDataResult<>(campaignService.getOneCampaign(id), "Data has been listed successfully."));
        }
        catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDataResult<>(e.getMessage()));
        }
    }
}
