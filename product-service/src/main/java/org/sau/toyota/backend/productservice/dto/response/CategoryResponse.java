package org.sau.toyota.backend.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.productservice.entity.Category;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a response DTO (Data Transfer Object) for a category.
 * Contains information such as the name and description of the category.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    /**
     * The name of the category.
     */
    private String name;

    /**
     * The description of the category.
     */
    private String description;

    /**
     * Converts a Category entity to a CategoryResponse object.
     *
     * @param category The Category entity to convert
     * @return A CategoryResponse object converted from the Category entity
     */
    public static CategoryResponse Convert(Category category){
        return CategoryResponse.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
