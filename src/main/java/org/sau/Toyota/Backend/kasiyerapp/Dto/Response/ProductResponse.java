package org.sau.Toyota.Backend.kasiyerapp.Dto.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Product;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private String name;

    private Double price;

    private byte[] image;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date expirationDate;

    private String description;

    private String brand;

    private byte[] barcode;

    private String category;

    public static ProductResponse Convert(Product product){
       return ProductResponse.builder()
               .name(product.getName())
               .price(product.getPrice())
               .image(product.getImage())
               .expirationDate(product.getExpirationDate())
               .description(product.getDescription())
               .brand(product.getBrand())
               .barcode(product.getBarcode())
               .category(product.getCategory().getName())
               .build();
    }
}
