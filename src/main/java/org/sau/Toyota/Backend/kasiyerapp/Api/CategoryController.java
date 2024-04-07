package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.*;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.CategoryRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CategoryResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getAllCategories")
    public DataResult<List<CategoryResponse>> getAllCategories(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int size,
                                                               @RequestParam(defaultValue = "id") String sortBy,
                                                               @RequestParam(defaultValue = "asc") String sortOrder,
                                                               @RequestParam(required = false) String filter){

        return new SuccessDataResult<>(categoryService.getAllCategories(page, size, sortBy, sortOrder, filter), "Data has been listed.");
    }

    @GetMapping("/getOneCategory/{id}")
    public DataResult<CategoryResponse> getOneCategory(@PathVariable("id") Long id){
        return new SuccessDataResult<>(categoryService.getOneCategory(id), "Data has been listed.");
    }

    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody CategoryRequest categoryRequest){
        try{
            categoryService.addCategory(categoryRequest);
            return new SuccessResult("Category added successfully.");
        }
        catch (IllegalArgumentException e){
            return new ErrorResult(e.getMessage());
        }
    }

    @DeleteMapping("deleteCategory/{id}")
    public Result deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategory(id);
            return new SuccessResult("Category is deleted successfully.");
        }
        catch (NullPointerException e){
            return new ErrorResult(e.getMessage());
        }
    }
}
