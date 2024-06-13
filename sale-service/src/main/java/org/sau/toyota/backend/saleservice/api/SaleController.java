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

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;
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
