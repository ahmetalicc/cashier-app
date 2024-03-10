package org.sau.Toyota.Backend.kasiyerapp.Service.Abstract;


import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortOrder, String filter);

    ProductResponse getOneProduct(Long id);

    List<ProductResponse> getProductsByCategoryId(Long id, int page, int size, String sortBy, String sortOrder);

    String addImg(MultipartFile file , Long id) throws IOException;

}
