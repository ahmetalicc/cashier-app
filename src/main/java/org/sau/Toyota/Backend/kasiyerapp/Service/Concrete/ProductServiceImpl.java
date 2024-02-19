package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dao.ProductRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.ProductResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Product;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

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
        Product product = productRepository.findById(id).orElseThrow();
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



}
