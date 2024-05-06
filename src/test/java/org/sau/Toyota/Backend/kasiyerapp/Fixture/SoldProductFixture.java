package org.sau.Toyota.Backend.kasiyerapp.Fixture;

import org.sau.Toyota.Backend.kasiyerapp.Entity.SoldProduct;

public class SoldProductFixture extends Fixture<SoldProduct>{

    ProductFixture productFixture = new ProductFixture();
    CampaignFixture campaignFixture = new CampaignFixture();
    SaleFixture saleFixture = new SaleFixture();
    public SoldProduct createSoldProduct(){
        SoldProduct soldProduct = new SoldProduct();
        soldProduct.setId(faker.number().randomNumber());
        soldProduct.setProduct(productFixture.createProduct());
        soldProduct.setCampaign(campaignFixture.createCampaign());
        soldProduct.setSale(saleFixture.createSale());
        soldProduct.setQuantity(20);

        return soldProduct;
    }
}
