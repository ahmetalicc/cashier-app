package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.sau.Toyota.Backend.kasiyerapp.Dao.CategoryRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.CategoryRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CategoryResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Category;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Product;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.CategoryService;
import org.springframework.dao.DataIntegrityViolationException;
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
    private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

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
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("Category not found with id: %s", id)));
        return CategoryResponse.Convert(category);
    }

    @Override
    public void addCategory(CategoryRequest categoryRequest) {
        if(categoryRequest == null){
            throw new IllegalArgumentException("Category request can not be null.");
        }
        String categoryName = categoryRequest.getName();
        String categoryDesc = categoryRequest.getDescription();

        if(categoryName == null || categoryName.trim().isEmpty()){
            logger.error("Category name is null or empty.");
            throw new IllegalArgumentException("Category name can not be null or empty.");
        }

        if(categoryDesc == null || categoryDesc.trim().isEmpty()){
            logger.error("Category description is null or empty.");
            throw new IllegalArgumentException("Category description can not be null or empty.");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setDescription(categoryDesc);
            categoryRepository.save(category);
            logger.info("Category is saved to the database successfully.");
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("Category not found with id: %s", id)));

        categoryRepository.delete(category);
        logger.info("Category is deleted with given id:" + id);
    }
}
