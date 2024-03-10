package org.sau.Toyota.Backend.kasiyerapp.Dto.Response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Sale;
import org.sau.Toyota.Backend.kasiyerapp.Entity.SoldProduct;
import org.sau.Toyota.Backend.kasiyerapp.Enum.PaymentType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleResponse {

    private Long saleNo;
    private List<SoldProductResponse> soldProducts;
    private double totalAmount;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime saleTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;
    private String cashierName;
    private double receivedAmount;
    private double changeAmount;
    private PaymentType paymentType;

    public static SaleResponse Convert(Sale sale, List<SoldProduct> soldProducts){

        List<SoldProductResponse> soldProductResponses = soldProducts.stream()
                .map(SoldProductResponse::Convert)
                .collect(Collectors.toList());

        return SaleResponse.builder()
                .saleNo(sale.getId())
                .soldProducts(soldProductResponses)
                .totalAmount(sale.getTotalAmount())
                .saleTime(sale.getSaleTime())
                .time(sale.getTime())
                .cashierName(sale.getCashierName())
                .receivedAmount(sale.getReceivedAmount())
                .changeAmount(sale.getChangeAmount())
                .paymentType(sale.getPaymentType())
                .build();


    }

}
