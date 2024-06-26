package org.sau.toyota.backend.productservice.api;

import lombok.RequiredArgsConstructor;
import org.sau.toyota.backend.productservice.core.results.*;
import org.sau.toyota.backend.productservice.dto.request.ProductRequest;
import org.sau.toyota.backend.productservice.dto.request.ProductUpdateRequest;
import org.sau.toyota.backend.productservice.dto.response.ProductResponse;
import org.sau.toyota.backend.productservice.service.Abstract.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * REST controller for managing products.
 * <p>
 * Provides endpoints to retrieve product data.
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    /**
     * Retrieves a list of products based on pagination and filtering options.
     *
     * @param page     Page number (default is 0)
     * @param size     Number of items per page (default is 3)
     * @param sortBy   Field name to sort by (default is "id")
     * @param sortOrder Sorting order ("asc" for ascending, "desc" for descending)
     * @param filter   Filter criteria (optional)
     * @return A {@link DataResult} containing a list of {@link ProductResponse} objects.
     */
    @GetMapping("/getAllProducts")
    public DataResult<List<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "3") int size,
                                                            @RequestParam(defaultValue = "id") String sortBy,
                                                            @RequestParam(defaultValue = "asc") String sortOrder,
                                                            @RequestParam(required = false) String filter){
        return new SuccessDataResult<>
                (productService.getAllProducts(page, size, sortBy, sortOrder, filter), "Data has been listed.");
    }
    /**
     * Retrieves a single product by its ID.
     *
     * @param id Product ID
     * @return A {@link DataResult} containing a {@link ProductResponse} object if the product is found,
     * or an {@link ErrorDataResult} if the product is not found or an exception occurs.
     */
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
    /**
     * Retrieves a list of products filtered by category ID, based on pagination and sorting options.
     *
     * @param id       Category ID for filtering products
     * @param page     Page number (default is 0)
     * @param size     Number of items per page (default is 3)
     * @param sortBy   Field name to sort by (default is "id")
     * @param sortOrder Sorting order ("asc" for ascending, "desc" for descending)
     * @return A {@link DataResult} containing a list of {@link ProductResponse} objects filtered by category ID.
     */
    @GetMapping("/getProductsByCategoryId/{id}")
    public DataResult<List<ProductResponse>> getProductsByCategoryId(@PathVariable("id") Long id,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "3") int size,
                                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                                     @RequestParam(defaultValue = "asc") String sortOrder){
        return new SuccessDataResult<>
                (productService.getProductsByCategoryId(id, page, size, sortBy, sortOrder), "Data has been listed.");
    }
    /**
     * Adds an image for a specific product.
     *
     * @param file Image file to be uploaded
     * @param id   Product ID to associate the image with
     * @return A string indicating the success or failure of the image upload process.
     * @throws IOException If an I/O error occurs during image upload.
     */
    @GetMapping("/addImg/{id}")
    public String addImg(@RequestParam("file") MultipartFile file, @PathVariable("id")Long id) throws IOException {
        try {
            return productService.addImg(file, id);
        }
        catch (NullPointerException e){
            return e.getMessage();
        }
   }
    /**
     * Retrieves the image associated with a specific product.
     *
     * @param id Product ID
     * @return A ResponseEntity containing the image data as a byte array with the appropriate content type,
     * or an error message if the image is not found.
     */
    @GetMapping("/getImg/{id}")
    public ResponseEntity<byte[]> getImg(@PathVariable Long id){
        try {
            byte[] imageData = productService.getImg(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (NullPointerException e) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>(e.getMessage().getBytes(), headers, HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Adds a new product to the database.
     *
     * @param productRequest The request object containing product details
     * @return A {@link DataResult} containing a {@link ProductResponse} object representing the added product,
     * or an {@link ErrorDataResult} if an exception occurs during the addition process.
     */
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
    /**
     * Deletes a product from database by its ID.
     *
     * @param id Product ID to delete
     * @return A {@link Result} indicating the success or failure of the deletion operation.
     */
    @DeleteMapping("deleteProduct/{id}")
    public Result deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new SuccessResult("Product is deleted successfully.");
        } catch (NullPointerException e) {
            return new ErrorResult(e.getMessage());
        }
    }
    /**
     * Updates the price and stock information of a product.
     *
     * @param id Product ID to update
     * @param productUpdateRequest The request object containing updated product details
     * @return A {@link Result} indicating the success or failure of the update operation,
     * along with the updated price and stock information.
     */
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
