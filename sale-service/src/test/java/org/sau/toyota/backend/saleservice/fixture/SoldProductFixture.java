package org.sau.toyota.backend.saleservice.fixture;


import org.sau.toyota.backend.saleservice.entity.SoldProduct;

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
