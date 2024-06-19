package org.sau.toyota.backend.saleservice.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.saleservice.Enum.PaymentType;
import org.sau.toyota.backend.saleservice.entity.Sale;
import org.sau.toyota.backend.saleservice.entity.SoldProduct;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * SaleResponse is a Data Transfer Object (DTO) that represents the details of a sale transaction.
 *
 * <p>
 * This class includes information about the sale such as sale number, sold products, total amount,
 * sale time, cashier name, received amount, change amount, and payment type. It also includes a
 * conversion method to transform a Sale entity and a list of SoldProduct entities into a SaleResponse.
 * </p>
 *
 * @see Sale
 * @see SoldProduct
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleResponse {
    /**
     * The unique identifier for the sale transaction.
     */
    private Long saleNo;
    /**
     * The list of products sold in this sale transaction.
     */
    private List<SoldProductResponse> soldProducts;
    /**
     * The total amount for the sale transaction.
     */
    private double totalAmount;
    /**
     * The date and time when the sale occurred.
     * This field is formatted as 'yyyy/MM/dd'.
     */
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime saleTime;
    /**
     * The time when the sale occurred.
     * This field is formatted as 'HH:mm:ss'.
     */
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime time;
    /**
     * The name of the cashier who handled the sale transaction.
     */
    private String cashierName;
    /**
     * The amount received from the customer.
     */
    private double receivedAmount;
    /**
     * The change amount returned to the customer.
     */
    private double changeAmount;
    /**
     * The type of payment used for the sale transaction.
     */
    private PaymentType paymentType;
    /**
     * Converts a Sale entity and a list of SoldProduct entities into a SaleResponse DTO.
     *
     * @param sale the Sale entity to be converted.
     * @param soldProducts the list of SoldProduct entities associated with the sale.
     * @return a SaleResponse DTO representing the sale details.
     */
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
