package org.sau.Toyota.Backend.kasiyerapp.Service.Abstract;


import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.ProductResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Category;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortOrder);

    ProductResponse getOneProduct(Long id);

    List<ProductResponse> getProductsByCategoryId(Long id);

    List<ProductResponse> getFilteredProducts(String name);
}
