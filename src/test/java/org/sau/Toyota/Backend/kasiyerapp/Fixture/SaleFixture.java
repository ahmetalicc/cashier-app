package org.sau.Toyota.Backend.kasiyerapp.Fixture;

import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.SaleRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.SoldProductRequest;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Sale;
import org.sau.Toyota.Backend.kasiyerapp.Enum.PaymentType;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SaleFixture extends Fixture<Sale>{

    public List<Sale> createSaleList(){

        List<Sale> sales = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Sale sale = Sale.builder()
                    .id(faker.number().randomNumber())
                    .totalAmount(faker.number().randomDouble(2, 50, 200))
                    .saleTime(faker.date().past(30, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .time(LocalTime.now().minusMinutes(faker.number().numberBetween(10, 60)))
                    .cashierName(faker.name().fullName())
                    .receivedAmount(faker.number().randomDouble(2, 100, 300))
                    .soldProducts(new ArrayList<>())
                    .changeAmount(faker.number().randomDouble(2, 0, 100))
                    .paymentType(faker.options().option(PaymentType.class))
                    .build();

            sales.add(sale);
        }

        return sales;
    }

    public Sale createSale() {

        Sale sale = new Sale();
        sale.setId(faker.number().randomNumber());
        sale.setTotalAmount(faker.number().randomDouble(2, 1, 10));
        sale.setSaleTime(faker.date().past(30, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        sale.setTime(LocalTime.now().minusMinutes(faker.number().numberBetween(10, 60)));
        sale.setCashierName(faker.name().fullName());
        sale.setReceivedAmount(faker.number().randomDouble(2, 1001, 2000));
        sale.setSoldProducts(new ArrayList<>());
        sale.setChangeAmount(faker.number().randomDouble(2, 0, 50));
        sale.setPaymentType(faker.options().option(PaymentType.class));

        return sale;
    }
    public SaleRequest createSaleRequest(int min , int max) {

        List<SoldProductRequest> soldProducts = new ArrayList<>();
        soldProducts.add(SoldProductRequest.builder()
                .productId(faker.number().randomNumber())
                .quantity(faker.number().numberBetween(0,30))
                .build());

        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setCashierName(faker.name().fullName());
        saleRequest.setPaymentType(faker.options().option(PaymentType.class));
        saleRequest.setReceivedAmount(faker.number().randomDouble(2, min, max));
        saleRequest.setSoldProducts(soldProducts);

        return saleRequest;
}

}
