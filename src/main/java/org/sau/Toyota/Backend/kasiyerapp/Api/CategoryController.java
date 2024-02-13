package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.CategoryResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.ProductResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getAllCategories")
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/getOneCategory/{id}")
    public CategoryResponse getOneCategory(@PathVariable("id") Long id){
        return categoryService.getOneCategory(id);
    }
}
