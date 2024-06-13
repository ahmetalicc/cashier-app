package org.sau.toyota.backend.saleservice.service.Concrete;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;

import org.sau.toyota.backend.saleservice.dao.CampaignRepository;
import org.sau.toyota.backend.saleservice.dao.ProductRepository;
import org.sau.toyota.backend.saleservice.dao.SaleRepository;
import org.sau.toyota.backend.saleservice.dao.SoldProductRepository;
import org.sau.toyota.backend.saleservice.dto.request.SaleRequest;
import org.sau.toyota.backend.saleservice.dto.response.SaleResponse;
import org.sau.toyota.backend.saleservice.entity.*;
import org.sau.toyota.backend.saleservice.service.Abstract.SaleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Log4j2
public class SaleServiceImpl implements SaleService {

    private final ProductRepository productRepository;

    private final CampaignRepository campaignRepository;

    private final SaleRepository saleRepository;

    private final SoldProductRepository soldProductRepository;

    @Override
    public SaleResponse makeSale(SaleRequest saleRequest) {


        Sale sale = new Sale();

        sale.setSaleTime(LocalDateTime.now());
        sale.setTime(LocalTime.now());
        sale.setCashierName(saleRequest.getCashierName());
        sale.setReceivedAmount(saleRequest.getReceivedAmount());
        sale.setPaymentType(saleRequest.getPaymentType());

        List<SoldProduct> soldProducts = saleRequest.getSoldProducts().stream()
                .map(soldProductRequest -> {
                    SoldProduct soldProduct = new SoldProduct();

                    Product product = productRepository.findById(soldProductRequest.getProductId())
                            .orElseThrow(()-> new RuntimeException(String.format("Product not found with id:%s", soldProductRequest.getProductId())));
                    soldProduct.setProduct(product);
                    soldProduct.setQuantity(soldProductRequest.getQuantity());

                    Category category = product.getCategory();

                    Campaign campaign = campaignRepository.findCampaignByCategoryId(category.getId());
                    soldProduct.setCampaign(campaign);
                    return soldProduct;
                })
                .collect(Collectors.toList());

        sale.setSoldProducts(soldProducts);

        double totalAmount = calculateTotalAmount(soldProducts);
        if(totalAmount > saleRequest.getReceivedAmount()){
            log.info(String.format("your budget is %s less than price", totalAmount - saleRequest.getReceivedAmount()));
            throw new RuntimeException(String.format("your budget is %s less than price", totalAmount - saleRequest.getReceivedAmount()));
        }
        sale.setTotalAmount(totalAmount);
        sale.setChangeAmount(saleRequest.getReceivedAmount() - totalAmount);

        updateStock(soldProducts);

        saleRepository.save(sale);

        log.info("Sale is saved to the database successfully.");

        soldProducts.forEach(soldProduct -> {
            soldProduct.setSale(sale);
            soldProductRepository.save(soldProduct);
        });

        log.info("Sold Products are saved to the database successfully.");

        return SaleResponse.Convert(sale, soldProducts);
    }


    private double calculateTotalAmount(List<SoldProduct> soldProducts){
        double totalAmount = 0;
        for(SoldProduct soldProduct : soldProducts){
            double productPrice = soldProduct.getProduct().getPrice();
            int quantity = soldProduct.getQuantity();
            double discount = calculateDiscount(soldProduct.getCampaign(), productPrice, quantity);
            totalAmount += ((productPrice * quantity) - discount);
        }
        return totalAmount;
    }
    private double calculateDiscount(Campaign campaign, double productPrice, int quantity) {
        double discount = 0;
        if (campaign != null) {
            Long campaignId = campaign.getCategory().getId();
            switch (campaignId.intValue()) {
                case 1:
                    int discountQuantity = quantity / 3;
                    discount = discountQuantity * productPrice;
                    break;
                case 2:
                    if (productPrice * quantity >= 150) {
                        discount = productPrice * quantity * 0.05;
                    }
                    break;
                case 3:
                    if (productPrice * quantity >= 500) {
                        discount = productPrice * quantity * 0.10;
                    }
                    break;
                case 4:
                    if (productPrice * quantity >= 100) {
                        discount = productPrice * quantity * 0.20;
                    }
                    break;
                case 5:
                    if (productPrice * quantity >= 300) {
                        discount = 50;
                    }
                    break;
                default:
                    break;
            }
        }
        return discount;
    }


    private void updateStock(List<SoldProduct> soldProducts){
        for (SoldProduct soldProduct : soldProducts){
            Product product = soldProduct.getProduct();
            int updatedStock = product.getStock() - soldProduct.getQuantity();
            product.setStock(updatedStock);
            productRepository.save(product);
        }
    }

}
