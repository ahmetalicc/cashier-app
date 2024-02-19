package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dao.CategoryRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CategoryResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Category;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Product;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAllCategories(int page, int size, String sortBy, String sortOrder, String filter) {
        Sort sort = Sort.by(sortBy).ascending();
        if ("desc".equals(sortOrder)) {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> categories;
        if(filter != null && !filter.isEmpty()){
            categories = categoryRepository.findCategoriesByNameOrDescriptionContains(filter, pageable);
        }else {
            categories = categoryRepository.findAll(pageable);
        }
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
