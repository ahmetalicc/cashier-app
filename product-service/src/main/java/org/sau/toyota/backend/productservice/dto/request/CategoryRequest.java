package org.sau.toyota.backend.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a request DTO (Data Transfer Object) for creating or updating a category.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    /**
     * The name of the category.
     */
    private String name;
    /**
     * The description of the category.
     */
    private String description;
}
