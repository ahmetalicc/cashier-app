package org.sau.Toyota.Backend.kasiyerapp.Fixture;

import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.ProductRequest;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Category;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Product;
import org.sau.Toyota.Backend.kasiyerapp.Entity.SoldProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ProductFixture extends Fixture<Product> {

    SaleFixture saleFixture = new SaleFixture();
    CategoryFixture categoryFixture = new CategoryFixture();
    CampaignFixture campaignFixture = new CampaignFixture();
    public List<Product> createProductList(){
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Product product = Product.builder()
                    .id(faker.number().randomNumber())
                    .name(faker.commerce().productName())
                    .price(faker.number().randomDouble(2, 10, 100))
                    .image(new byte[]{})
                    .description(faker.lorem().sentence())
                    .brand(faker.company().name())
                    .expirationDate(faker.date().future(365, TimeUnit.DAYS))
                    .stock(faker.number().numberBetween(100, 1000))
                    .barcode(new byte[]{})
                    .category(categoryFixture.createCategory())
                    .build();
            products.add(product);
        }

        return products;
    }

    public Product createProduct(){
        Product product = new Product();
        product.setId(faker.number().randomNumber());
        product.setName(faker.commerce().productName());
        product.setPrice(faker.number().randomDouble(2, 0, 25));
        product.setImage(new byte[]{});
        product.setDescription(faker.lorem().sentence());
        product.setBrand(faker.company().name());
        product.setExpirationDate(faker.date().future(365, TimeUnit.DAYS));
        product.setStock(faker.number().numberBetween(100, 1000));
        product.setBarcode(new byte[]{});
        product.setCategory(categoryFixture.createCategory());

        return product;
}

    public ProductRequest createProductRequest(){
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName(faker.commerce().productName());
        productRequest.setPrice(faker.number().randomDouble(2, 10, 100));
        productRequest.setDescription(faker.lorem().sentence());
        productRequest.setBrand(faker.company().name());
        productRequest.setExpirationDate(faker.date().future(365, TimeUnit.DAYS));
        productRequest.setStock(faker.number().numberBetween(100, 1000));
        productRequest.setBarcode(new byte[]{});
        productRequest.setCategoryId(categoryFixture.createCategory().getId());

        return productRequest;
    }

    public SoldProduct createSoldProduct(){
        SoldProduct soldProduct = new SoldProduct();
        soldProduct.setId(faker.number().randomNumber());
        soldProduct.setProduct(createProduct());
        soldProduct.setCampaign(campaignFixture.createCampaign());
        soldProduct.setSale(saleFixture.createSale());
        soldProduct.setQuantity(faker.number().numberBetween(1, 20));

        return soldProduct;
    }
}
