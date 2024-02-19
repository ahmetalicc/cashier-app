package org.sau.Toyota.Backend.kasiyerapp.Api;


import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.DataResult;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.ErrorDataResult;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.ErrorResult;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.SuccessDataResult;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.SaleRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.SaleResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.SaleService;
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
