package org.sau.toyota.backend.productservice.api;

import lombok.RequiredArgsConstructor;
import org.sau.toyota.backend.productservice.core.results.*;
import org.sau.toyota.backend.productservice.dto.request.CategoryRequest;
import org.sau.toyota.backend.productservice.dto.response.CategoryResponse;
import org.sau.toyota.backend.productservice.service.Abstract.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * REST controller for managing categories.
 * <p>
 * Provides endpoints to retrieve category data.
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Retrieves a list of categories based on pagination and filtering options.
     *
     * @param page     Page number (default is 0)
     * @param size     Number of items per page (default is 3)
     * @param sortBy   Field name to sort by (default is "id")
     * @param sortOrder Sorting order ("asc" for ascending, "desc" for descending)
     * @param filter   Filter criteria (optional)
     * @return A {@link DataResult} containing a list of {@link CategoryResponse} objects.
     */
    @GetMapping("/getAllCategories")
    public ResponseEntity<DataResult<List<CategoryResponse>>> getAllCategories(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "3") int size,
                                                                              @RequestParam(defaultValue = "id") String sortBy,
                                                                              @RequestParam(defaultValue = "asc") String sortOrder,
                                                                              @RequestParam(required = false) String filter){

        return ResponseEntity.ok( new SuccessDataResult<>(categoryService.getAllCategories(page, size, sortBy, sortOrder, filter), "Data has been listed successfully."));
    }

    /**
     * Retrieves a single category by its ID.
     *
     * @param id Category ID
     * @return A {@link DataResult} containing a {@link CategoryResponse} object if the category is found,
     * or an {@link ErrorDataResult} if the category is not found or an exception occurs.
     */
    @GetMapping("/getOneCategory/{id}")
    public ResponseEntity<DataResult<CategoryResponse>> getOneCategory(@PathVariable("id") Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessDataResult<>(categoryService.getOneCategory(id), "Data has been listed successfully."));
        }
        catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ErrorDataResult<>(e.getMessage()));
        }
    }

    /**
     * Adds a new category.
     *
     * @param categoryRequest The request body containing category information.
     * @return A {@link Result} indicating the success or failure of the operation.
     */
    @PostMapping("/addCategory")
    public ResponseEntity<Result> addCategory(@RequestBody CategoryRequest categoryRequest){
        try{
            categoryService.addCategory(categoryRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("Category added successfully."));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResult(e.getMessage()));
        }
    }
    /**
     * Deletes a category by its ID.
     *
     * @param id Category ID to delete.
     * @return A {@link Result} indicating the success or failure of the operation.
     */
    @DeleteMapping("deleteCategory/{id}")
    public ResponseEntity<Result> deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategory(id);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResult("Category is deleted successfully."));
        }
        catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResult(e.getMessage()));
        }
    }
}
