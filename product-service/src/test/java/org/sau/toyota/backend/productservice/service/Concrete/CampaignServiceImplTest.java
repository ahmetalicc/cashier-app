package org.sau.toyota.backend.productservice.service.Concrete;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sau.toyota.backend.productservice.fixture.CampaignFixture;
import org.sau.toyota.backend.productservice.dao.CampaignRepository;
import org.sau.toyota.backend.productservice.dto.response.CampaignResponse;
import org.sau.toyota.backend.productservice.entity.Campaign;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
/** @author Ahmet Alıç
 * @since 14-06-2024
 * Unit tests for {@link CampaignServiceImpl} class.
 */
@ExtendWith(MockitoExtension.class)
public class CampaignServiceImplTest {


    @Mock
    private CampaignRepository campaignRepository;
    @InjectMocks
    private CampaignServiceImpl campaignService;

    @DisplayName("The test when call with page size sortBy sortOrder and filter parameters that should return CampaignResponse list with pagination")
    @ParameterizedTest
    @ValueSource(strings = {"test", "asd", "123", "ahmet"})
    void getAllCampaigns_whenCallWithFilter_ShouldReturnCampaignResponseListWithPagination(String filter){
        CampaignFixture campaignFixture = new CampaignFixture();
        List<Campaign> campaignList = campaignFixture.createCampaignList();
        Page<Campaign> pagedCampaigns = new PageImpl<>(campaignList);

        when(campaignRepository.findCampaignsByNameOrDescriptionContains(eq(filter), any(Pageable.class)))
                .thenReturn(pagedCampaigns);

        List<CampaignResponse> actual = campaignService.getAllCampaigns(0, 10, "name", "asc", filter);
        List<CampaignResponse> expected = pagedCampaigns.stream()
                .map(CampaignResponse::Convert)
                .toList();

        assertEquals(actual, expected);
        assertNotNull(filter);
        verify(campaignRepository, times(1)).findCampaignsByNameOrDescriptionContains(anyString(), any(Pageable.class));
    }

    @DisplayName("The test when call with page size sortBy and sortOrder parameters that should return CampaignResponse list with pagination")
    @Test
    void getAllCampaigns_whenCallWithoutFilter_ShouldReturnCampaignResponseListWithPagination(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "asc";

        CampaignFixture campaignFixture = new CampaignFixture();
        List<Campaign> campaignList = campaignFixture.createCampaignList();
        Page<Campaign> pagedCampaigns = new PageImpl<>(campaignList);
        when(campaignRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy).ascending())))
                .thenReturn(pagedCampaigns);

        // Act
        List<CampaignResponse> actual = campaignService.getAllCampaigns(page, size, sortBy, sortOrder, null);
        List<CampaignResponse> expected = pagedCampaigns.stream()
                .map(CampaignResponse::Convert)
                .toList();

        // Assert
        assertEquals(actual, expected);
        verify(campaignRepository, times(1)).findAll(any(Pageable.class));
    }

    @DisplayName("The test when call without filter that should sorts Campaigns in descending order when sort order is descending")
    @Test
    void getAllCampaigns_whenCallWithoutFilter_ShouldSortsCampaignsDescending_whenSortOrderIsDescending(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "desc";

        CampaignFixture campaignFixture = new CampaignFixture();
        List<Campaign> campaignList = campaignFixture.createCampaignList();
        Page<Campaign> pagedCampaigns = new PageImpl<>(campaignList);
        when(campaignRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy).descending())))
                .thenReturn(pagedCampaigns);

        List<CampaignResponse> actual = campaignService.getAllCampaigns(page, size, sortBy, sortOrder, null);
        List<CampaignResponse> expected = pagedCampaigns.stream()
                .map(CampaignResponse::Convert)
                .toList();

        assertEquals(actual, expected);
        verify(campaignRepository, times(1)).findAll(any(Pageable.class));
    }

    @DisplayName("The test when call with id parameter that should return CampaignResponse if Campaign exist with given id")
    @Test
    void getOneCampaign_whenCallWithIdParameter_ShouldReturnCampaignResponse_IfCampaignExistWithGivenId(){
        CampaignFixture campaignFixture = new CampaignFixture();
        Campaign campaign = campaignFixture.createCampaign();
        Long id = campaign.getId();

        when(campaignRepository.findById(id)).thenReturn(Optional.of(campaign));

        CampaignResponse actual = campaignService.getOneCampaign(id);
        CampaignResponse expected = CampaignResponse.Convert(campaign);

        assertEquals(actual, expected);
        assertNotNull(actual);

        verify(campaignRepository, times(1)).findById(id);
    }
    @DisplayName("The test that should throw Null Pointer Exception if Campaign is not exist with given id")
    @Test()
    void getOneCampaign_ShouldThrowNullPointerException_IfCampaignIsNotExistWithGivenId(){
        CampaignFixture campaignFixture = new CampaignFixture();
        Campaign campaign = campaignFixture.createCampaign();
        Long id = campaign.getId();
        when(campaignRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> campaignService.getOneCampaign(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Campaign not found with id: %s", id));

        verify(campaignRepository, times(1)).findById(id);
    }
}
