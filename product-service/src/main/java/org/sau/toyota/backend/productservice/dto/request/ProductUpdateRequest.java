package org.sau.toyota.backend.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a request DTO (Data Transfer Object) for updating a product.
 * Contains fields such as price and stock to specify the updated details of the product.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {
    /**
     * The updated price of the product.
     */
    private Double price;

    /**
     * The updated stock of the product.
     */
    private int stock;
}
