package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dao.CategoryRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CategoryResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.ProductResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Category;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Product;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponse::Convert)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getOneCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return CategoryResponse.Convert(category);
    }
}
