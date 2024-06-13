package org.sau.toyota.backend.saleservice.dto.request;

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
