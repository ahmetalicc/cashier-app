package org.sau.toyota.backend.saleservice.api;


import lombok.RequiredArgsConstructor;
import org.sau.toyota.backend.saleservice.core.results.DataResult;
import org.sau.toyota.backend.saleservice.core.results.ErrorDataResult;
import org.sau.toyota.backend.saleservice.core.results.SuccessDataResult;
import org.sau.toyota.backend.saleservice.dto.request.SaleRequest;
import org.sau.toyota.backend.saleservice.dto.response.SaleResponse;
import org.sau.toyota.backend.saleservice.service.Abstract.SaleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * REST controller handling sale-related operations.
 * This controller provides endpoints for processing sale requests.
 */
@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;
    /**
     * POST endpoint for processing a sale request.
     *
     * @param saleRequest The request object containing sale details.
     * @return A DataResult containing the SaleResponse if the sale is successful, or an ErrorDataResult if an error occurs.
     * If successful, the response message indicates that the sale processes are completed successfully.
     * If an error occurs during the sale processing, the error message is included in the response.
     */
    @PostMapping("/makeSale")
    public DataResult<SaleResponse> makeSale(@RequestBody SaleRequest saleRequest){
        try{
            SaleResponse saleResponse = saleService.makeSale(saleRequest);
            return new SuccessDataResult<>(saleResponse, "Sale processes are completed successfully");
        }
        catch (RuntimeException e){
            return new ErrorDataResult<>(e.getMessage());
        }
    }


}
