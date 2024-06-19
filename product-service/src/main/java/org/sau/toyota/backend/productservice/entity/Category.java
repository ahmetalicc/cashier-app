package org.sau.toyota.backend.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Represents a category entity, which contains information about a product category.
 * Each category has an ID, name, description, associated products, and associated campaigns.
 *
 * @see Product
 * @see Campaign
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class Category {

    /**
     * The unique identifier for the Category entity.
     * The implementing strategy is defined as identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The name of the category which is not nullable and can have maximum length of 50 characters.
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    /**
     * The description of the category which is not nullable and can have maximum length of 1000 characters.
     */
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    /**
     * The list of products associated with the category.
     * This is a one-to-many relationship with the {@link Product} entity.
     * Each category can have multiple products.
     */
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "category")
    private List<Product> products;
    /**
     * The list of campaigns associated with the category.
     * This is a one-to-many relationship with the {@link Campaign} entity.
     * Each category can have multiple campaigns.
     */
    @OneToMany(cascade = CascadeType.ALL,
             mappedBy = "category")
    private List<Campaign> campaigns;


}
