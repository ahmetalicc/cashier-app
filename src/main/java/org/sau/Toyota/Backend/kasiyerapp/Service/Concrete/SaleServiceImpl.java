package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.sau.Toyota.Backend.kasiyerapp.Dao.CampaignRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dao.ProductRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dao.SaleRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dao.SoldProductRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.SaleRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.SaleResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.*;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.SaleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Builder
public class SaleServiceImpl implements SaleService {

    private static final Logger logger = Logger.getLogger(SaleServiceImpl.class);

    private final ProductRepository productRepository;

    private final CampaignRepository campaignRepository;

    private final SaleRepository saleRepository;

    private final SoldProductRepository soldProductRepository;

    @Override
    public SaleResponse makeSale(SaleRequest saleRequest) {


        Sale sale = new Sale();

        sale.setSaleTime(LocalDateTime.now());
        sale.setCashierName(saleRequest.getCashierName());
        sale.setReceivedAmount(saleRequest.getReceivedAmount());
        sale.setPaymentType(saleRequest.getPaymentType());

        List<SoldProduct> soldProducts = saleRequest.getSoldProducts().stream()
                .map(soldProductRequest -> {
                    SoldProduct soldProduct = new SoldProduct();

                    Product product = productRepository.findById(soldProductRequest.getProductId()).orElseThrow();
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
            logger.info(String.format("your budget is %s less than price", totalAmount - saleRequest.getReceivedAmount()));
            throw new RuntimeException(String.format("your budget is %s less than price", totalAmount - saleRequest.getReceivedAmount()));
        }
        sale.setTotalAmount(totalAmount);
        sale.setChangeAmount(saleRequest.getReceivedAmount() - totalAmount);

        updateStock(soldProducts);

        saleRepository.save(sale);

        soldProducts.forEach(soldProduct -> {
            soldProduct.setSale(sale);
            soldProductRepository.save(soldProduct);
        });


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

    private double calculateDiscount(Campaign campaign, double productPrice, int quantity){
        double discount = 0;
        if(campaign != null){
            Long campaignId = campaign.getCategory().getId();
            if (campaignId == 1) {
                int discountQuantity = quantity / 3;
                discount = discountQuantity * productPrice;
            } else if (campaignId == 2) {
                if(productPrice * quantity >= 150){
                    discount = productPrice * quantity * 0.05;
                }
            } else if (campaignId == 3) {
                if (productPrice * quantity >= 500) {
                    discount = productPrice * quantity * 0.10;
                }
            } else if (campaignId == 4) {
                if (productPrice * quantity >= 100) {
                    discount = productPrice * quantity * 0.20;
                }
            } else if (campaignId == 5) {
                if (productPrice * quantity >= 300) {
                    discount = 50;
                }
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
