package org.sau.toyota.backend.productservice.service.Abstract;


import org.sau.toyota.backend.productservice.dto.request.CategoryRequest;
import org.sau.toyota.backend.productservice.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {


    List<CategoryResponse> getAllCategories(int page, int size, String sortBy, String sortOrder, String filter);


    CategoryResponse getOneCategory(Long id);

    void addCategory(CategoryRequest categoryRequest);

    void deleteCategory(Long id);
}
