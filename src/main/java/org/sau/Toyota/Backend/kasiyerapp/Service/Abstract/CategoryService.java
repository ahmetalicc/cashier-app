package org.sau.Toyota.Backend.kasiyerapp.Service.Abstract;

import org.sau.Toyota.Backend.kasiyerapp.Dao.CategoryRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CategoryResponse;

import java.util.List;

public interface CategoryService {


    List<CategoryResponse> getAllCategories();


    CategoryResponse getOneCategory(Long id);
}
