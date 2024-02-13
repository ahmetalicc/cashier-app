package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "3") int size,
                                                                @RequestParam(defaultValue = "id") String sortBy,
                                                                @RequestParam(defaultValue = "asc") String sortOrder){
        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, sortOrder));
    }

    @GetMapping("/getOneProduct/{id}")
    public ProductResponse getOneProduct(@PathVariable("id") Long id){
        return productService.getOneProduct(id);
    }

    @GetMapping("/getProductsByCategoryId/{id}")
    public List<ProductResponse> getProductsByCategoryId(@PathVariable("id") Long id){
        return productService.getProductsByCategoryId(id);
    }

    @GetMapping("/getFilteredProducts")
    public ResponseEntity<List<ProductResponse>> getFilteredProducts(@RequestParam("searchTerm") String searchTerm){
        return ResponseEntity.ok(productService.getFilteredProducts(searchTerm));
    }



}
