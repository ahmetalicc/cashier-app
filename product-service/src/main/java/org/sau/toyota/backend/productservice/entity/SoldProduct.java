package org.sau.toyota.backend.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * SoldProduct is an entity class that represents a product sold in a sale transaction.
 *
 * <p>
 * This class maps to the "soldProduct" table in the database and includes details such as
 * the product, quantity sold, and the sale and campaign associated with the product sale.
 * </p>
 *
 * <p>
 * The class also uses JPA annotations to define the table mapping and relationships between entities.
 * </p>
 * @see SoldProduct
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "soldProduct")
public class SoldProduct {
    /**
     * The unique identifier for the {@link SoldProduct} entity.
     * The implementing strategy is defined as identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The product that was sold.
     * This is a many-to-one relationship with the {@link Product} entity.
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    /**
     * The quantity of the product sold in the transaction.
     * Quantity can not be nullable and can have maximum length of 50 characters.
     */
    @Column(name = "quantity", length = 50, nullable = false)
    private int quantity;
    /**
     * The sale transaction in which this product was sold.
     * This is a many-to-one relationship with the {@link Sale} entity.
     */
    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
    /**
     * The campaign associated with the sold product, if any.
     * This is a many-to-one relationship with the {@link Campaign} entity.
     */
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
}
