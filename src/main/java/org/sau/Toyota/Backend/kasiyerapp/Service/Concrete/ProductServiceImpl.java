package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.sau.Toyota.Backend.kasiyerapp.Dao.CategoryRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dao.ProductRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.ProductRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.ProductUpdateRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.ProductResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Category;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Product;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    @Override
    public List<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortOrder, String filter) {
        Sort sort = Sort.by(sortBy).ascending();
        if ("desc".equals(sortOrder)) {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products;
        if(filter != null && !filter.isEmpty()){
            products = productRepository.findProductsByNameOrDescriptionContains(filter, pageable);
        }else {
            products = productRepository.findAll(pageable);
        }
        return products.stream()
                .map(ProductResponse::Convert)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getOneProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("Product not found with id: %s", id)));
        return ProductResponse.Convert(product);
    }

    @Override
    public List<ProductResponse> getProductsByCategoryId(Long id, int page, int size, String sortBy, String sortOrder) {

        Sort sort = Sort.by(sortBy).ascending();
        if ("desc".equals(sortOrder)) {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productRepository.findAllProductsByCategoryId(id, pageable);

        return products.stream()
                .map(ProductResponse::Convert)
                .collect(Collectors.toList());
    }

    @Override
    public String addImg(MultipartFile file, Long id) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("Product not found with id: %s", id)));
        product.setImage(file.getBytes());
        productRepository.save(product);
        logger.info(String.format("Image saved to the database with product id: %s", id));
        byte[] imageData = product.getImage();
        return Base64.getEncoder().encodeToString(imageData);
    }

    @Override
    public String getImg(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("Product not found with id: %s", id)));
        byte[] imageData = product.getImage();
        return Base64.getEncoder().encodeToString(imageData);
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {

        if(productRequest == null){
            throw new IllegalArgumentException("Product request can not be null.");
        }
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setExpirationDate(productRequest.getExpirationDate());
        product.setDescription(productRequest.getDescription());
        product.setStock(productRequest.getStock());
        product.setBrand(productRequest.getBrand());
        product.setBarcode(productRequest.getBarcode());

        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                ()-> new NullPointerException(String.format("Category not found with id: %s", productRequest.getCategoryId())));
        product.setCategory(category);

        productRepository.save(product);
        logger.info("Product is saved to the database successfully");

        return ProductResponse.Convert(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("Product not found with id: %s", id)));
        productRepository.delete(product);
        logger.info(String.format("Product is deleted with given id: %s", id));
    }

    @Override
    public void updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("Product not found with id: %s", id)));

        product.setPrice(productUpdateRequest.getPrice());
        product.setStock(productUpdateRequest.getStock());
        productRepository.save(product);
        logger.info(String.format("Product's price and stock information are updated successfully. " +
                "The values for price and stock respectively: %s, %s", productUpdateRequest.getPrice(), productUpdateRequest.getStock()));
    }


}
