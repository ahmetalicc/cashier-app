package org.sau.Toyota.Backend.kasiyerapp.Dto.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String name;

    private Double price;

   // @JsonFormat(pattern = "yyyy/MM/dd")
    private Date expirationDate;

    private String description;

    private int stock;

    private String brand;

    private byte[] barcode;

    private Long categoryId;
}
