package org.sau.toyota.backend.saleservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.saleservice.entity.SoldProduct;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoldProductResponse {
    private String productName;
    private int quantity;
    private double price;


    public static SoldProductResponse Convert(SoldProduct soldProduct){
        return SoldProductResponse.builder()
                .productName(soldProduct.getProduct().getName())
                .quantity(soldProduct.getQuantity())
                .price(soldProduct.getProduct().getPrice())
                .build();
    }
}
