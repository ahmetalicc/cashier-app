package org.sau.Toyota.Backend.kasiyerapp.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Entity.SoldProduct;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoldProductResponse {
    private String productName;
    private int quantity;
    private double price;
    private String brand;

    public static SoldProductResponse Convert(SoldProduct soldProduct){
        return SoldProductResponse.builder()
                .productName(soldProduct.getProduct().getName())
                .quantity(soldProduct.getQuantity())
                .price(soldProduct.getProduct().getPrice())
                .brand(soldProduct.getProduct().getBrand())
                .build();
    }
}
