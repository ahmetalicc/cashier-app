package org.sau.toyota.backend.reportservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.reportservice.entity.SoldProduct;

/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * SoldProductResponse is a Data Transfer Object (DTO) that represents the details of a sold product.
 *
 * <p>
 * This class includes information about the product such as product name, quantity sold, and price.
 * It also includes a conversion method to transform a SoldProduct entity into a SoldProductResponse.
 * </p>
 *
 * @see SoldProduct
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoldProductResponse {
    /**
     * The name of the product.
     */
    private String productName;
    /**
     * The quantity of the product that was sold.
     */
    private int quantity;
    /**
     * The price of the product.
     */
    private double price;
    /**
     * Converts a SoldProduct entity into a SoldProductResponse DTO.
     *
     * @param soldProduct the SoldProduct entity to be converted.
     * @return a SoldProductResponse DTO representing the sold product details.
     */
    public static SoldProductResponse Convert(SoldProduct soldProduct){
        return SoldProductResponse.builder()
                .productName(soldProduct.getProduct().getName())
                .quantity(soldProduct.getQuantity())
                .price(soldProduct.getProduct().getPrice())
                .build();
    }
}
