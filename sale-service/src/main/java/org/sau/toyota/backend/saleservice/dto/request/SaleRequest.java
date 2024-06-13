package org.sau.toyota.backend.saleservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.saleservice.Enum.PaymentType;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleRequest {

    private List<SoldProductRequest> soldProducts;

    private double receivedAmount;

    private PaymentType paymentType;

    private String cashierName;


}
