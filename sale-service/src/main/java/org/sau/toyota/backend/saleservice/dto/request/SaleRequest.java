package org.sau.toyota.backend.saleservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.saleservice.Enum.PaymentType;

import java.util.List;

/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Represents a request DTO (Data Transfer Object) for creating a sale transaction.
 * This class contains information about the sold products, received amount, payment type, and cashier name.
 * @see org.sau.toyota.backend.saleservice.entity.Sale
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleRequest {
    /**
     * The list of sold products in the sale request.
     */
    private List<SoldProductRequest> soldProducts;
    /**
     * The amount received for the sale transaction.
     */
    private double receivedAmount;
    /**
     * The payment type for the sale transaction.
     */
    private PaymentType paymentType;
    /**
     * The name of the cashier who processed the sale transaction.
     */
    private String cashierName;


}
