package org.sau.toyota.backend.saleservice.fixture;


import org.sau.toyota.backend.saleservice.entity.SoldProduct;

/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * This class provides methods to create sample SoldProduct objects for testing purposes.
 */
public class SoldProductFixture extends Fixture<SoldProduct>{

    ProductFixture productFixture = new ProductFixture();
    CampaignFixture campaignFixture = new CampaignFixture();
    SaleFixture saleFixture = new SaleFixture();
    /**
     * Creates a SoldProduct object with randomly generated fake data.
     *
     * @return A SoldProduct object with random data generated for testing purposes.
     */
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
