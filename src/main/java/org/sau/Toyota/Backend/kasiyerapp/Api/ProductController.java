package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.DataResult;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.SuccessDataResult;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.ProductResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAllProducts")
    public DataResult<List<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "3") int size,
                                                            @RequestParam(defaultValue = "id") String sortBy,
                                                            @RequestParam(defaultValue = "asc") String sortOrder,
                                                            @RequestParam(required = false) String filter){
        return new SuccessDataResult<>
                (productService.getAllProducts(page, size, sortBy, sortOrder, filter), "Data has been listed.");
    }

    @GetMapping("/getOneProduct/{id}")
    public DataResult<ProductResponse> getOneProduct(@PathVariable("id") Long id){
        return new SuccessDataResult<>
                (productService.getOneProduct(id), "Data has been listed.");
    }

    @GetMapping("/getProductsByCategoryId/{id}")
    public DataResult<List<ProductResponse>> getProductsByCategoryId(@PathVariable("id") Long id,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "3") int size,
                                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                                     @RequestParam(defaultValue = "asc") String sortOrder){
        return new SuccessDataResult<>
                (productService.getProductsByCategoryId(id, page, size, sortBy, sortOrder), "Data has been listed.");
    }


}
