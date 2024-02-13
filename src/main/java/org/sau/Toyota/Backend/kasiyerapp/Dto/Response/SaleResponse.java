package org.sau.Toyota.Backend.kasiyerapp.Dto.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Sale;
import org.sau.Toyota.Backend.kasiyerapp.Entity.SoldProduct;
import org.sau.Toyota.Backend.kasiyerapp.Enum.PaymentType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleResponse {

    private Long id;
    private List<SoldProductResponse> soldProducts;
    private double totalAmount;
    private LocalDateTime saleTime;
    private String cashierName;
    private double receivedAmount;
    private double changeAmount;
    private PaymentType paymentType;

    public static SaleResponse Convert(Sale sale, List<SoldProduct> soldProducts){

        List<SoldProductResponse> soldProductResponses = soldProducts.stream()
                .map(SoldProductResponse::Convert)
                .collect(Collectors.toList());

        return SaleResponse.builder()
                .id(sale.getId())
                .soldProducts(soldProductResponses)
                .totalAmount(sale.getTotalAmount())
                .saleTime(sale.getSaleTime())
                .cashierName(sale.getCashierName())
                .receivedAmount(sale.getReceivedAmount())
                .changeAmount(sale.getChangeAmount())
                .paymentType(sale.getPaymentType())
                .build();


    }

}
