package org.sau.Toyota.Backend.kasiyerapp.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Enum.PaymentType;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleRequest {

    private List<SoldProductRequest> soldProducts;

    private double receivedAmount;

    private PaymentType paymentType;

    private LocalDateTime saleTime;

    private String cashierName;


}
