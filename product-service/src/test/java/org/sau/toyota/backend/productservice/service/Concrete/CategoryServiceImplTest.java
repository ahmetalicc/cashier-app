package org.sau.toyota.backend.productservice.service.Concrete;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sau.toyota.backend.productservice.fixture.CategoryFixture;
import org.sau.toyota.backend.productservice.dao.CategoryRepository;
import org.sau.toyota.backend.productservice.dto.request.CategoryRequest;
import org.sau.toyota.backend.productservice.dto.response.CategoryResponse;
import org.sau.toyota.backend.productservice.entity.Category;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** @author Ahmet Alıç
 * @since 14-06-2024
 * Unit tests for {@link CategoryServiceImpl} class.
 */
@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private CategoryServiceImpl categoryService;


    @DisplayName("The test when call with page size sortBy sortOrder and filter that should return CategoryResponse list with pagination")
    @ParameterizedTest
    @ValueSource(strings = {"test", "asd", "123", "ahmet"})
    void getAllCategories_whenCallWithFilter_ShouldReturnCategoryResponseListWithPagination(String filter){
        CategoryFixture categoryFixture = new CategoryFixture();
        List<Category> categoryList = categoryFixture.createCategoryList();
        Page<Category> pagedCategories = new PageImpl<>(categoryList);

        when(categoryRepository.findCategoriesByNameOrDescriptionContains(eq(filter), any(Pageable.class)))
                .thenReturn(pagedCategories);

        List<CategoryResponse> actual = categoryService.getAllCategories(0, 10, "name", "asc", filter);
        List<CategoryResponse> expected = pagedCategories.stream()
                .map(CategoryResponse::Convert)
                .toList();

        assertEquals(actual, expected);
        assertNotNull(filter);

        verify(categoryRepository, times(1)).findCategoriesByNameOrDescriptionContains(anyString(), any(Pageable.class));
    }

    @DisplayName("The test when call with page size sortBy and sortOrder parameters that should return CategoryResponse list with pagination")
    @Test
    void getAllCategories_whenCallWithoutFilter_ShouldReturnCategoryResponseListWithPagination(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "asc";

        CategoryFixture categoryFixture = new CategoryFixture();
        List<Category> categoryList = categoryFixture.createCategoryList();
        Page<Category> pagedCategories = new PageImpl<>(categoryList);
        when(categoryRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy).ascending())))
                .thenReturn(pagedCategories);

        List<CategoryResponse> actual = categoryService.getAllCategories(page, size, sortBy, sortOrder, null);
        List<CategoryResponse> expected = pagedCategories.stream()
                .map(CategoryResponse::Convert)
                .toList();

        assertEquals(actual, expected);

        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }

    @DisplayName("The test when call without filter that should sorts Categories in descending order when sort order is descending")
    @Test
    void getAllCategories_whenCallWithoutFilter_ShouldSortsCategoriesDescending_whenSortOrderIsDescending(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "desc";

        CategoryFixture categoryFixture = new CategoryFixture();
        List<Category> categoryList = categoryFixture.createCategoryList();
        Page<Category> pagedCategories = new PageImpl<>(categoryList);
        when(categoryRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy).descending())))
                .thenReturn(pagedCategories);

        List<CategoryResponse> actual = categoryService.getAllCategories(page, size, sortBy, sortOrder, null);
        List<CategoryResponse> expected = pagedCategories.stream()
                .map(CategoryResponse::Convert)
                .toList();

        assertEquals(actual, expected);

        verify(categoryRepository, times(1)).findAll(any(Pageable.class));
    }
    @DisplayName("The test when call with id parameter that should return CategoryResponse if Category exist with given id ")
    @Test
    void getOneCategory_whenCallWithIdParameter_ShouldReturnCategoryResponse_IfCategoryExistWithGivenId(){
        CategoryFixture categoryFixture = new CategoryFixture();
        Category category = categoryFixture.createCategory();
        Long id = category.getId();

        when(categoryRepository.findById(id))
                .thenReturn(Optional.of(category));

        CategoryResponse actual = categoryService.getOneCategory(id);
        CategoryResponse expected = CategoryResponse.Convert(category);

        assertEquals(actual, expected);
        assertNotNull(actual);

        verify(categoryRepository, times(1)).findById(id);

    }

    @DisplayName("The test that should throw Null Pointer Exception if Category is not exist with given id")
    @Test()
    void getOneCategory_ShouldThrowNullPointerException_IfCategoryIsNotExistWithGivenId(){
        CategoryFixture categoryFixture = new CategoryFixture();
        Category category = categoryFixture.createCategory();
        Long id = category.getId();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> categoryService.getOneCategory(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Category not found with id: %s", id));

        verify(categoryRepository, times(1)).findById(id);
    }

    @DisplayName("The test when call with valid CategoryRequest that should add that new CategoryRequest to the database")
    @Test
    void addCategory_whenCallWithValidCategoryRequest_ShouldSaveNewCategoryToTheDatabase(){
        CategoryRequest categoryRequest = new CategoryRequest("Test Category", "Test Description");
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryService.addCategory(categoryRequest);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @DisplayName("The test when CategoryRequest is null that should Throw Illegal Argument Exception")
    @Test
    void addCategory_whenCategoryRequestIsNull_ShouldThrowIllegalArgumentException(){
        assertThatThrownBy(()-> categoryService.addCategory(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Category request can not be null.");

    }

    @DisplayName("The test when CategoryName is null that should throw Illegal Argument Exception")
    @Test
    void addCategory_whenCategoryNameIsNull_ShouldThrowIllegalArgumentException(){
        CategoryRequest categoryRequest = new CategoryRequest(null, "Description 1");

        assertThatThrownBy(()-> categoryService.addCategory(categoryRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Category name can not be null or empty.");

        assertNull(categoryRequest.getName());
    }

    @DisplayName("The test when CategoryName is empty that should throw Illegal Argument Exception")
    @Test
    void addCategory_whenCategoryNameIsEmpty_ShouldThrowIllegalArgumentException(){
        CategoryRequest categoryRequest = new CategoryRequest("", "Description 1");

        assertThatThrownBy(()-> categoryService.addCategory(categoryRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Category name can not be null or empty.");

        assertTrue(categoryRequest.getName().isEmpty());
    }

    @DisplayName("The test when CategoryDescription is null that should throw Illegal Argument Exception")
    @Test
    void addCategory_whenCategoryDescriptionIsNull_ShouldThrowIllegalArgumentException(){
        CategoryRequest categoryRequest = new CategoryRequest("Category 1", null);

        assertThatThrownBy(()-> categoryService.addCategory(categoryRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Category description can not be null or empty.");

        assertNull(categoryRequest.getDescription());
    }

    @DisplayName("The test when CategoryDescription is empty that should throw Illegal Argument Exception")
    @Test
    void addCategory_whenCategoryDescriptionIsEmpty_ShouldThrowIllegalArgumentException(){
        CategoryRequest categoryRequest = new CategoryRequest("Category 1", "");

        assertThatThrownBy(()-> categoryService.addCategory(categoryRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Category description can not be null or empty.");

        assertTrue(categoryRequest.getDescription().isEmpty());
    }

    @DisplayName("The test when Category exist with given id that should delete the Category")
    @Test
    void deleteCategory_whenCategoryExistWithGivenId_ShouldDeleteCategory(){
        CategoryFixture categoryFixture = new CategoryFixture();
        Category category = categoryFixture.createCategory();
        Long id = category.getId();

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(id);

        assertNotNull(id);
        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, times(1)).delete(category);
    }

    @DisplayName("The test when Category is not exist with given id that should throw Null Pointer Exception")
    @Test
    void deleteCategory_whenCategoryIsNotExistWithGivenId_ShouldThrowNullPointerException(){
        CategoryFixture categoryFixture = new CategoryFixture();
        Category category = categoryFixture.createCategory();
        Long id = category.getId();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        
        assertThatThrownBy(()-> categoryService.deleteCategory(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Category not found with id: %s", id));

        verify(categoryRepository, times(1)).findById(id);
    }
}
