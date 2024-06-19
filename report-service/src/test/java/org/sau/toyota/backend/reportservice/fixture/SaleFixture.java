package org.sau.toyota.backend.reportservice.fixture;


import org.sau.toyota.backend.reportservice.Enum.PaymentType;
import org.sau.toyota.backend.reportservice.entity.Sale;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * This class provides methods to create sample Sale objects for testing purposes.
 */
public class SaleFixture extends Fixture<Sale> {

    /**
     * Creates a list of Sale objects with randomly generated fake data.
     *
     * @return List of Sale objects
     */
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
    /**
     * Creates a single Sale object with randomly generated fake data.
     *
     * @return Sale object
     */
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

}
