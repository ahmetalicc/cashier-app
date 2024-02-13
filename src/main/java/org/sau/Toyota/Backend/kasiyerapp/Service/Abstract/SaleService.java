package org.sau.Toyota.Backend.kasiyerapp.Service.Abstract;

import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.SaleRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.SaleResponse;

public interface SaleService {

    SaleResponse makeSale(SaleRequest saleRequest);
}
