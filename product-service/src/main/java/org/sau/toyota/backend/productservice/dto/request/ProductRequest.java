package org.sau.toyota.backend.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a request DTO (Data Transfer Object) for creating or updating a product.
 * Contains fields such as name, price, expiration date, description, stock, brand,
 * barcode, and category ID to specify the product details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    /**
     * The name of the product.
     */
    private String name;

    /**
     * The price of the product.
     */
    private Double price;

    /**
     * The expiration date of the product.
     */
    private Date expirationDate;

    /**
     * The description of the product.
     */
    private String description;

    /**
     * The available stock of the product.
     */
    private int stock;

    /**
     * The brand of the product.
     */
    private String brand;

    /**
     * The barcode image data of the product.
     */
    private byte[] barcode;

    /**
     * The ID of the category to which the product belongs.
     */
    private Long categoryId;
}
