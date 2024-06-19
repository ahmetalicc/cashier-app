package org.sau.toyota.backend.saleservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Represents a request DTO (Data Transfer Object) for a sold product in a sale transaction.
 * This class contains information about the product ID and the quantity sold.
 * @see org.sau.toyota.backend.saleservice.entity.SoldProduct
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoldProductRequest {

    private Long productId;
    private int quantity;
}
