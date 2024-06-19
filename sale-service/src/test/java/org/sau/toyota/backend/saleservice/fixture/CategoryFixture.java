package org.sau.toyota.backend.saleservice.fixture;


import org.sau.toyota.backend.saleservice.entity.Category;

import java.util.ArrayList;
import java.util.List;
/** @author Ahmet Alıç
 * @since 15-06-2024
 * Fixture class for generating Category objects for testing purposes.
 */
public class CategoryFixture extends Fixture<Category> {

    /**
     * Creates a list of Category objects with randomly generated fake data.
     *
     * @return List of Category objects
     */
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
    /**
     * Creates a single Category object with randomly generated fake data.
     *
     * @return Category object
     */
    public Category createCategory(){
        Category category = new Category();
        category.setId(faker.number().randomNumber());
        category.setName(faker.gameOfThrones().character());
        category.setDescription(faker.lorem().sentence());

        return category;
    }
}

