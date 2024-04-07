package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.*;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.ProductRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.ProductUpdateRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.ProductResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        try {
            return new SuccessDataResult<>
                    (productService.getOneProduct(id), "Data has been listed.");
        }
        catch (NullPointerException e){
            return new ErrorDataResult<>(e.getMessage());
        }
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

    @GetMapping("/addImg/{id}")
    public String addImg(@RequestParam("file") MultipartFile file, @PathVariable("id")Long id) throws IOException {
        try {
            return productService.addImg(file, id);
        }
        catch (NullPointerException e){
            return e.getMessage();
        }
   }

   @GetMapping("/getImg/{id}")
    public String getImg(@PathVariable Long id){
        try{
            return productService.getImg(id);
        }
        catch (NullPointerException e){
            return e.getMessage();
        }
   }
   @PostMapping("/addProduct")
    public DataResult<ProductResponse> addProduct(@RequestBody ProductRequest productRequest){
        try {
            return new SuccessDataResult<>
                    (productService.addProduct(productRequest), "Product is saved to the database successfully.");
        }
        catch (IllegalArgumentException | NullPointerException e){
            return new ErrorDataResult<>(e.getMessage());
        }
   }

    @DeleteMapping("deleteProduct/{id}")
    public Result deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new SuccessResult("Product is deleted successfully.");
        } catch (NullPointerException e) {
            return new ErrorResult(e.getMessage());
        }
    }

    @PutMapping("/updateProduct/{id}")
    public Result updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest productUpdateRequest){
        try {
            productService.updateProduct(id, productUpdateRequest);
            return new SuccessResult(String.format("Product's price and stock information are updated successfully. " +
                            "The values for price and stock respectively: %s, %s",
                    productUpdateRequest.getPrice(), productUpdateRequest.getStock()));
        }catch (NullPointerException e){
            return new ErrorResult(e.getMessage());
        }
    }


}
