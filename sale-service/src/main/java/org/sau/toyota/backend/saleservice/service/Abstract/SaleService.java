package org.sau.toyota.backend.saleservice.service.Abstract;

import org.sau.toyota.backend.saleservice.dto.request.SaleRequest;
import org.sau.toyota.backend.saleservice.dto.response.SaleResponse;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * The interface defines the contract for sale operations.
 */
public interface SaleService {

    /**
     * Makes a sale based on the provided sale request.
     *
     * @param saleRequest The sale request containing information about sold products, payment details, and cashier name.
     * @return A SaleResponse object representing the result of the sale operation.
     */
    SaleResponse makeSale(SaleRequest saleRequest);
}
