package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sau.Toyota.Backend.kasiyerapp.Dao.CategoryRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dao.ProductRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.ProductRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.ProductUpdateRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.ProductResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Product;
import org.sau.Toyota.Backend.kasiyerapp.Fixture.ProductFixture;
import org.springframework.data.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @DisplayName("The test when call with page size sortBy sortOrder and filter parameters that should return list of ProductResponse with pagination")
    @ParameterizedTest
    @ValueSource(strings = {"test", "asd", "123", "ahmet"})
    void getAllProducts_whenCallWithFilter_ShouldReturnListOfProductResponseWithPagination(String filter){
        ProductFixture productFixture = new ProductFixture();
        List<Product> productList = productFixture.createProductList();
        Page<Product> pagedProducts = new PageImpl<>(productList);

        when(productRepository.findProductsByNameOrDescriptionContains(eq(filter), any(Pageable.class)))
                .thenReturn(pagedProducts);

        List<ProductResponse> actual = productService.getAllProducts(0, 10, "name", "asc", filter);
        List<ProductResponse> expected = pagedProducts.stream()
                .map(ProductResponse::Convert)
                .toList();

        assertEquals(actual, expected);
        assertNotNull(filter);
        verify(productRepository, times(1)).findProductsByNameOrDescriptionContains(anyString(), any(Pageable.class));
    }

    @DisplayName(" The test when call with page size sortBy and sortOrder parameters that should return list of ProductResponse with pagination")
    @Test
    void getAllProducts_whenCallWithoutFilter_ShouldReturnListOfProductResponseWithPagination(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "asc";
        String filter = null;

        ProductFixture productFixture = new ProductFixture();
        List<Product> productList = productFixture.createProductList();
        Page<Product> pagedProducts = new PageImpl<>(productList);
        when(productRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy).ascending())))
                .thenReturn(pagedProducts);

        // Act
        List<ProductResponse> actual = productService.getAllProducts(page, size, sortBy, sortOrder, filter);
        List<ProductResponse> expected = pagedProducts.stream()
                .map(ProductResponse::Convert)
                .toList();

        // Assert
        assertEquals(actual, expected);
        verify(productRepository, times(1)).findAll(any(Pageable.class));
    }

    @DisplayName("The test when call without filter that should sorts Products in descending order when sort order is descending")
    @Test
    void getAllProducts_whenCallWithoutFilter_ShouldSortsProductsDescending_whenSortOrderIsDescending(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "desc";

        ProductFixture productFixture = new ProductFixture();
        List<Product> productList = productFixture.createProductList();
        Page<Product> pagedProducts = new PageImpl<>(productList);
        when(productRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy).descending())))
                .thenReturn(pagedProducts);

        // Act
        List<ProductResponse> actual = productService.getAllProducts(page, size, sortBy, sortOrder, null);
        List<ProductResponse> expected = pagedProducts.stream()
                .map(ProductResponse::Convert)
                .toList();

        // Assert
        assertEquals(actual, expected);
        verify(productRepository, times(1)).findAll(any(Pageable.class));
    }

    @DisplayName("The test when call with id parameter that should return ProductResponse if Product exist with given id")
    @Test
    void getOneProduct_whenCallWithIdParameter_ShouldReturnProductResponse_IfProductExistWithGivenId(){
        ProductFixture productFixture = new ProductFixture();
        Product product = productFixture.createProduct();
        Long id = product.getId();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ProductResponse actual = productService.getOneProduct(id);
        ProductResponse expected = ProductResponse.Convert(product);

        assertEquals(actual, expected);
        assertNotNull(actual);
        verify(productRepository, times(1)).findById(id);
    }

    @DisplayName("The test that should throw Null Pointer Exception if Product is not exist with given id")
    @Test()
    void getOneProduct_ShouldThrowNullPointerException_IfProductIsNotExistWithGivenId(){
        ProductFixture productFixture = new ProductFixture();
        Product product = productFixture.createProduct();
        Long id = product.getId();

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> productService.getOneProduct(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Product not found with id: %s", id));

        verify(productRepository, times(1)).findById(id);
    }

    @DisplayName("The test when call with valid parameters that should return list of ProductResponse with pagination")
    @Test
    void getProductsByCategoryId_whenCallWithValidParameters_ShouldReturnListOfProductResponseWithPagination(){
        Long id = 1L;
        int page = 0;
        int size = 3;
        String sortBy = "name";
        String sortOrder = "asc";

        ProductFixture productFixture = new ProductFixture();
        List<Product> productList = productFixture.createProductList();
        Page<Product> pagedProducts = new PageImpl<>(productList);
        when(productRepository.findAllProductsByCategoryId(id, PageRequest.of(page, size, Sort.by(sortBy).ascending())))
                .thenReturn(pagedProducts);

        List<ProductResponse> actual = productService.getProductsByCategoryId(id, page, size, sortBy, sortOrder);
        List<ProductResponse> expected = pagedProducts.stream()
                .map(ProductResponse::Convert)
                .toList();

        assertEquals(actual, expected);
        assertNotNull(actual);
        verify(productRepository, times(1)).findAllProductsByCategoryId(any(Long.class), any(Pageable.class));
    }

    @DisplayName("The test when call without filter that should sorts Products descending when sort order is descending")
    @Test
    void getProductsByCategoryId_whenCallWithValidParameters_ShouldSortsProductsDescending_whenSortOrderIsDescending(){
        Long id = 1L;
        int page = 0;
        int size = 3;
        String sortBy = "name";
        String sortOrder = "desc";

        ProductFixture productFixture = new ProductFixture();
        List<Product> productList = productFixture.createProductList();
        Page<Product> pagedProducts = new PageImpl<>(productList);
        when(productRepository.findAllProductsByCategoryId(id, PageRequest.of(page, size, Sort.by(sortBy).descending())))
                .thenReturn(pagedProducts);

        List<ProductResponse> actual = productService.getProductsByCategoryId(id, page, size, sortBy, sortOrder);
        List<ProductResponse> expected = pagedProducts.stream()
                .map(ProductResponse::Convert)
                .toList();

        assertEquals(actual, expected);
        assertNotNull(actual);
        verify(productRepository, times(1)).findAllProductsByCategoryId(any(Long.class), any(Pageable.class));
    }

    @DisplayName("The test when call with valid parameters and product exist with given id that should set product image and should return image in string format")
    @Test
    void addImg_whenProductExistWithGivenId_ShouldSetProductImageAnd_ShouldReturnThatImageInStringFormat() throws IOException {
        Long id = 1L;
        byte[] imageData = "test image data".getBytes();
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn(imageData);

        ProductFixture productFixture = new ProductFixture();
        Product product = productFixture.createProduct();
        product.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        String actual = productService.addImg(file, id);

        assertNotNull(actual);
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(product);
    }

    @DisplayName("The test that should throw Null Pointer Exception if product not exist with given id")
    @Test
    void addImg_ShouldThrowNullPointerException_IfProductNotExistWithGivenId(){
        ProductFixture productFixture = new ProductFixture();
        Product product = productFixture.createProduct();
        Long id = product.getId();
        MultipartFile file = mock(MultipartFile.class);

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> productService.addImg(file, id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Product not found with id: %s", id));

        verify(productRepository, times(1)).findById(id);
    }

    @DisplayName("The test when call with id parameter and product exist with given id that should return image of product in String format")
    @Test
    void getImg_whenProductExistWithGivenId_ShouldReturnImageOfProductInStringFormat(){
        ProductFixture productFixture = new ProductFixture();
        Product product = productFixture.createProduct();
        Long id = product.getId();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        String actual = productService.getImg(id);

        assertNotNull(actual);
        verify(productRepository, times(1)).findById(id);
    }

    @DisplayName("The test that should throw Null Pointer Exception if product not exist with given id")
    @Test
    void getImg_ShouldThrowNullPointerException_IfProductNotExistWithGivenId(){
        ProductFixture productFixture = new ProductFixture();
        Product product = productFixture.createProduct();
        Long id = product.getId();

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> productService.getImg(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Product not found with id: %s", id));

        verify(productRepository, times(1)).findById(id);
    }

    @DisplayName("The test when call with valid ProductRequest that should save new product to the database and should return ProductResponse")
    @Test
    void addProduct_whenCallWithValidProductRequest_ShouldSaveNewProductToTheDBAnd_ShouldReturnProductResponse(){
        ProductFixture productFixture = new ProductFixture();
        ProductRequest productRequest = productFixture.createProductRequest();
        Long categoryId = productRequest.getCategoryId();
        Product product = productFixture.createProduct();

        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(product.getCategory()));

        ProductResponse actual = productService.addProduct(productRequest);
        ProductResponse expected = ProductResponse.Convert(product);

        assertNotNull(actual);
        assertNotNull(expected);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @DisplayName("The test when ProductRequest is null that should Throw Illegal Argument Exception")
    @Test
    void addProduct_whenProductRequestIsNull_ShouldThrowIllegalArgumentException(){
        assertThatThrownBy(()-> productService.addProduct(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product request can not be null.");
    }

    @DisplayName("The test that should throw Null Pointer Exception when category of ProductRequest is not exist ")
    @Test
    void addProduct_whenCategoryOfProductRequestIsNotExist_ShouldThrowNullPointerException(){
        ProductFixture productFixture = new ProductFixture();
        ProductRequest productRequest = productFixture.createProductRequest();
        Long id = productRequest.getCategoryId();

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> productService.addProduct(productRequest))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Category not found with id: %s", id));

        verify(categoryRepository, times(1)).findById(id);
    }

    @DisplayName("The test when Product exist with given id that should delete the Product")
    @Test
    void deleteProduct_whenProductExistWithGivenId_ShouldDeleteProduct(){
        ProductFixture productFixture = new ProductFixture();
        Product product = productFixture.createProduct();
        Long id = product.getId();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productService.deleteProduct(id);

        assertNotNull(id);
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).delete(product);
    }

    @DisplayName("The test when Product is not exist with given id that should throw Null Pointer Exception")
    @Test
    void deleteProduct_whenProductIsNotExistWithGivenId_ShouldThrowNullPointerException(){
        ProductFixture productFixture = new ProductFixture();
        Product product = productFixture.createProduct();
        Long id = product.getId();

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> productService.deleteProduct(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Product not found with id: %s", id));

        verify(productRepository, times(1)).findById(id);
    }

    @DisplayName("The test when valid id and ProductUpdateRequest parameters should update and save the product to the DB")
    @Test
    void updateProduct_whenValidIdAndProductUpdateRequestParameters_ShouldUpdateAndSaveTheProductToTheDB(){
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest(30.0, 200);
        ProductFixture productFixture = new ProductFixture();
        Product product = productFixture.createProduct();
        Long id = product.getId();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productService.updateProduct(id, productUpdateRequest);

        assertNotNull(id);
        assertNotNull(productUpdateRequest);
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct_whenProductIsNotExistWithGivenId_ShouldThrowNullPointerException(){
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest(30.0, 200);
        ProductFixture productFixture = new ProductFixture();
        Product product = productFixture.createProduct();
        Long id = product.getId();

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> productService.updateProduct(id, productUpdateRequest))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Product not found with id: %s", id));

        verify(productRepository, times(1)).findById(id);
    }

}
