package org.sau.toyota.backend.saleservice.service.Abstract;

import org.sau.toyota.backend.saleservice.dto.request.SaleRequest;
import org.sau.toyota.backend.saleservice.dto.response.SaleResponse;

public interface SaleService {

    SaleResponse makeSale(SaleRequest saleRequest);
}
