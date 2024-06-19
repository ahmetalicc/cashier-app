package org.sau.toyota.backend.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * <p>
 * This class maps to the "products" table in the database and includes details such as the
 * product's name, price, image, description, brand, expiration date, stock, and barcode.
 * It also defines relationships with the {@link Category} and {@link SoldProduct} entities.
 * </p>
 *
 * <p>
 * The class also uses JPA annotations to define the table mapping and relationships between entities.
 * </p>
 * @see Category
 * @see SoldProduct
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    /**
     * The unique identifier for the {@link Product} entity.
     * The implementing strategy is defined as identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The name of the product which is not nullable and can have maximum length of 50 characters.
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    /**
     * The price of the product which is not nullable and can have maximum length of 50 characters.
     */
    @Column(name = "price", length = 50, nullable = false)
    private Double price;
    /**
     * The image of the product.
     */
    @Column(name = "image")
    private byte[] image;
    /**
     * The description of the product which is not nullable and can have maximum length of 1000 characters.
     */
    @Column(name = "description", length = 1000, nullable = false)
    private String description;
    /**
     * The brand of the product which is not nullable and can have maximum length of 50 characters.
     */
    @Column(name = "brand", length = 50, nullable = false)
    private String brand;
    /**
     * The expiration date of the product which is not nullable.
     */
    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;
    /**
     * The stock of the product which is not nullable.
     */
    @Column(name = "stock", nullable = false)
    private int stock;
    /**
     * The expiration date of the product which is unique.
     */
    @Column(name = "barcode", unique = true)
    private byte[] barcode;
    /**
     * The category to which the product belongs.
     * This is a many-to-one relationship with the {@link Category} entity.
     * Each product belongs to one category.
     */
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
    /**
     * The list of sold products associated with this product.
     * This is a one-to-many relationship with the {@link SoldProduct} entity.
     * Each product can have multiple sold instances.
     */
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product")
    private List<SoldProduct> soldProducts;

}
