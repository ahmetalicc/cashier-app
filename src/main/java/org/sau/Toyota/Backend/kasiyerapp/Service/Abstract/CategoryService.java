package org.sau.Toyota.Backend.kasiyerapp.Service.Abstract;

import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CategoryResponse;

import java.util.List;

public interface CategoryService {


    List<CategoryResponse> getAllCategories(int page, int size, String sortBy, String sortOrder, String filter);


    CategoryResponse getOneCategory(Long id);
}
