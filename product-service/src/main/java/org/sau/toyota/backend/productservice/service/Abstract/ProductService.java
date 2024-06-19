package org.sau.toyota.backend.productservice.service.Abstract;


import org.sau.toyota.backend.productservice.dto.request.ProductRequest;
import org.sau.toyota.backend.productservice.dto.request.ProductUpdateRequest;
import org.sau.toyota.backend.productservice.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * This service interface provides methods to manage products.
 * It defines operations to retrieve all products, get a single product by ID,
 * get products by category ID, add a new product, delete a product, and update a product.
 */
public interface ProductService {
    /**
     * Retrieves a list of all products based on pagination and filtering options.
     *
     * @param page      Page number
     * @param size      Number of items per page
     * @param sortBy    Field to sort by
     * @param sortOrder Sort order (asc for ascending, desc for descending)
     * @param filter    Filter criteria to apply
     * @return List of ProductResponse objects representing products
     */
    List<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortOrder, String filter);
    /**
     * Retrieves a single product by its ID.
     *
     * @param id Product ID
     * @return ProductResponse object representing the product
     */
    ProductResponse getOneProduct(Long id);
    /**
     * Retrieves a list of products based on the category ID.
     *
     * @param id        Category ID
     * @param page      Page number
     * @param size      Number of items per page
     * @param sortBy    Field to sort by
     * @param sortOrder Sort order (asc for ascending, desc for descending)
     * @return List of ProductResponse objects representing products
     */
    List<ProductResponse> getProductsByCategoryId(Long id, int page, int size, String sortBy, String sortOrder);
    /**
     * Adds an image to a product.
     *
     * @param file Image file as MultipartFile
     * @param id   Product ID to associate the image with
     * @return string
     */
    String addImg(MultipartFile file , Long id) throws IOException;
    /**
     * Retrieves an image of a product.
     *
     * @param id Product ID
     * @return string
     */
    String getImg(Long id);
    /**
     * Adds a new product to the database.
     *
     * @param productRequest ProductRequest object containing product information
     * @return ProductResponse object representing the added product
     */
    ProductResponse addProduct(ProductRequest productRequest);
    /**
     * Deletes a product from the database by its ID.
     *
     * @param id Product ID to be deleted
     */
    void deleteProduct(Long id);
    /**
     * Updates a product's price and stock information.
     *
     * @param id                   Product ID to be updated
     * @param productUpdateRequest ProductUpdateRequest object containing updated price and stock information
     */
    void updateProduct(Long id, ProductUpdateRequest productUpdateRequest);
}
