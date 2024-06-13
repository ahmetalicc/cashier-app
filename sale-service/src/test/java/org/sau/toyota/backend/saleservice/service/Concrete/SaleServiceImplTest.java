package org.sau.toyota.backend.saleservice.service.Concrete;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sau.toyota.backend.saleservice.fixture.ProductFixture;
import org.sau.toyota.backend.saleservice.fixture.SaleFixture;
import org.sau.toyota.backend.saleservice.fixture.SoldProductFixture;
import org.sau.toyota.backend.saleservice.dao.CampaignRepository;
import org.sau.toyota.backend.saleservice.dao.ProductRepository;
import org.sau.toyota.backend.saleservice.dao.SaleRepository;
import org.sau.toyota.backend.saleservice.dao.SoldProductRepository;
import org.sau.toyota.backend.saleservice.dto.request.SaleRequest;
import org.sau.toyota.backend.saleservice.dto.response.SaleResponse;
import org.sau.toyota.backend.saleservice.entity.Product;
import org.sau.toyota.backend.saleservice.entity.Sale;
import org.sau.toyota.backend.saleservice.entity.SoldProduct;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CampaignRepository campaignRepository;
    @Mock
    private SaleRepository saleRepository;
    @Mock
    private SoldProductRepository soldProductRepository;
    @Mock
    private Logger logger;
    @InjectMocks
    private SaleServiceImpl saleService;

    @DisplayName("The test that when SaleRequest parameter is valid should return SaleResponse")
    @Test
    void makeSale_whenSaleRequestParameterIsValid_ShouldReturnSaleResponse(){
        SaleFixture saleFixture = new SaleFixture();
        ProductFixture productFixture = new ProductFixture();

        SaleRequest saleRequest = saleFixture.createSaleRequest(1001,2000);

        Product product = productFixture.createProduct();

        when(saleRepository.save(any(Sale.class))).thenReturn(new Sale());
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        SaleResponse saleResponse = saleService.makeSale(saleRequest);

        assertNotNull(saleResponse);
        assertNotNull(saleResponse.getSaleTime());
        assertEquals(saleRequest.getCashierName(), saleResponse.getCashierName());
        assertEquals(saleRequest.getReceivedAmount(), saleResponse.getReceivedAmount());
        assertEquals(saleRequest.getPaymentType(), saleResponse.getPaymentType());

        verify(productRepository, times(1)).findById(anyLong());
        verify(saleRepository, times(1)).save(any(Sale.class));
        verify(soldProductRepository, times(1)).save(any(SoldProduct.class));
    }

    @DisplayName("The test that when the budget is insufficient should throw Run Time Exception")
    @Test
    void makeSale_whenTheBudgetIsInsufficient_ShouldThrowRunTimeException(){
        SaleFixture saleFixture = new SaleFixture();
        SoldProductFixture soldProductFixture = new SoldProductFixture();

        SaleRequest saleRequest = saleFixture.createSaleRequest(0,29);
        SoldProduct soldProduct = soldProductFixture.createSoldProduct();
        Product product = soldProduct.getProduct();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        assertThrows(RuntimeException.class, ()-> saleService.makeSale(saleRequest));

        verify(productRepository, times(1)).findById(anyLong());
    }



}
