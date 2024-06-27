package org.sau.toyota.backend.productservice.service.Concrete;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;
import org.sau.toyota.backend.productservice.dao.CategoryRepository;
import org.sau.toyota.backend.productservice.dto.request.CategoryRequest;
import org.sau.toyota.backend.productservice.dto.response.CategoryResponse;
import org.sau.toyota.backend.productservice.entity.Category;
import org.sau.toyota.backend.productservice.service.Abstract.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
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
            log.debug("Filtering categories with filter: {}", filter);
        }else {
            categories = categoryRepository.findAll(pageable);
        }
        return categories.stream()
                .map(CategoryResponse::Convert)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getOneCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("Category not found with id: {}", id);
                    return new NullPointerException(String.format("Category not found with id: %s", id));
                });
        return CategoryResponse.Convert(category);
    }

    @Override
    public void addCategory(CategoryRequest categoryRequest) {
        if(categoryRequest == null){
            log.error("Category request is null.");
            throw new IllegalArgumentException("Category request can not be null.");
        }
        String categoryName = categoryRequest.getName();
        String categoryDesc = categoryRequest.getDescription();

        if(categoryName == null || categoryName.trim().isEmpty()){
            log.error("Category name is null or empty.");
            throw new IllegalArgumentException("Category name can not be null or empty.");
        }

        if(categoryDesc == null || categoryDesc.trim().isEmpty()){
            log.error("Category description is null or empty.");
            throw new IllegalArgumentException("Category description can not be null or empty.");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setDescription(categoryDesc);
            categoryRepository.save(category);
            log.info("Category is saved to the database successfully.");
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Category not found with id: {}", id);
                    return new NullPointerException(String.format("Category not found with id: %s", id));
                });
        categoryRepository.delete(category);
        log.info("Category is deleted with given id:" + id);
    }
}
