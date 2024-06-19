package org.sau.toyota.backend.productservice.service.Abstract;


import org.sau.toyota.backend.productservice.dto.request.CategoryRequest;
import org.sau.toyota.backend.productservice.dto.response.CategoryResponse;

import java.util.List;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * This service interface provides methods to manage categories.
 * It defines operations to retrieve all categories, get a single category by ID,
 * add a new category, and delete a category.
 */
public interface CategoryService {

    /**
     * Retrieves a list of all categories based on pagination and filtering options.
     *
     * @param page      Page number
     * @param size      Number of items per page
     * @param sortBy    Field to sort by
     * @param sortOrder Sort order (asc for ascending, desc for descending)
     * @param filter    Filter criteria to apply
     * @return List of CategoryResponse objects representing categories
     */
    List<CategoryResponse> getAllCategories(int page, int size, String sortBy, String sortOrder, String filter);
    /**
     * Retrieves a single category by its ID.
     *
     * @param id Category ID
     * @return CategoryResponse object representing the category
     */
    CategoryResponse getOneCategory(Long id);
    /**
     * Adds a new category to the database.
     *
     * @param categoryRequest CategoryRequest object containing category information
     */
    void addCategory(CategoryRequest categoryRequest);
    /**
     * Deletes a category from the database by its ID.
     *
     * @param id Category ID to be deleted
     */
    void deleteCategory(Long id);
}
