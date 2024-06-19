package org.sau.toyota.backend.productservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.productservice.entity.Product;

import java.util.Date;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a response DTO (Data Transfer Object) for a product.
 * Contains information such as the name, price, expiration date, description, stock, brand, barcode, and category of the product.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

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
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date expirationDate;

    /**
     * The description of the product.
     */
    private String description;

    /**
     * The stock quantity of the product.
     */
    private int stock;

    /**
     * The brand of the product.
     */
    private String brand;

    /**
     * The barcode of the product.
     */
    private byte[] barcode;

    /**
     * The category to which the product belongs.
     */
    private String category;

    /**
     * Converts a Product entity to a ProductResponse object.
     *
     * @param product The Product entity to convert
     * @return A ProductResponse object converted from the Product entity
     */
    public static ProductResponse Convert(Product product){
       return ProductResponse.builder()
               .name(product.getName())
               .price(product.getPrice())
               .expirationDate(product.getExpirationDate())
               .description(product.getDescription())
               .stock(product.getStock())
               .brand(product.getBrand())
               .barcode(product.getBarcode())
               .category(product.getCategory().getName())
               .build();
    }
}
