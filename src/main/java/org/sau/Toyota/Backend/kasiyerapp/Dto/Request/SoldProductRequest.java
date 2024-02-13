package org.sau.Toyota.Backend.kasiyerapp.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoldProductRequest {

    private Long productId;
    private int quantity;
}
