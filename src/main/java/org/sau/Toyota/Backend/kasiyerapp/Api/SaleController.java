package org.sau.Toyota.Backend.kasiyerapp.Api;


import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.SaleRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.SaleResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;
    @PostMapping("/makeSale")
    public ResponseEntity<Object> makeSale(@RequestBody SaleRequest saleRequest){
        try{
            SaleResponse saleResponse = saleService.makeSale(saleRequest);
            return ResponseEntity.ok(saleResponse);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
