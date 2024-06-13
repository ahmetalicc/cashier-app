package org.sau.toyota.backend.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;

    private Double price;

    private Date expirationDate;

    private String description;

    private int stock;

    private String brand;

    private byte[] barcode;

    private Long categoryId;
}
