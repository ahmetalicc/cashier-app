package org.sau.toyota.backend.productservice.fixture;


import org.sau.toyota.backend.productservice.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryFixture extends Fixture<Category> {

    public List<Category> createCategoryList(){
        List<Category> categories = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Category category = Category.builder()
                    .id(faker.number().randomNumber())
                    .name(faker.company().name())
                    .description(faker.lorem().sentence())
                    .build();
            categories.add(category);
        }

        return categories;
    }

    public Category createCategory(){
        Category category = new Category();
        category.setId(faker.number().randomNumber());
        category.setName(faker.gameOfThrones().character());
        category.setDescription(faker.lorem().sentence());

        return category;
    }}
