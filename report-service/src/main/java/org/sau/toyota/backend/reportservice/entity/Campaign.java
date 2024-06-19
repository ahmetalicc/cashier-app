package org.sau.toyota.backend.reportservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/** @author Ahmet Alıç
 * @since 15-06-2024
 * Campaign is an entity class that represents a promotional campaign within the system.
 *
 * <p>
 * This class maps to the "campaigns" table in the database and includes details such as the
 * campaign's name, description, associated category, and the list of sold products linked to this campaign.
 * </p>
 *
 * <p>
 * The class also uses JPA annotations to define the table mapping and relationships between entities.
 * </p>
 *
 * @see Category
 * @see SoldProduct
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "campaigns")
public class Campaign {

    /**
     * The unique identifier for the {@link Campaign} entity.
     * The implementing strategy is defined as identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the campaign which not nullable and can have maximum length of 50 characters.
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    /**
     * The description of the campaign which is not nullable and can have maximum length of 1000 characters.
     */
    @Column(name = "description", length = 1000, nullable = false)
    private String description;
    /**
     * The category to which the campaign is associated.
     * This is a many-to-one relationship with the {@link Category} entity.
     * Each campaign is associated with one category.
     */
    @ManyToOne()
    @JoinColumn( name = "category_id")
    private Category category;
    /**
     * The list of sold products associated with the campaign.
     * This is a one-to-many relationship with the {@link SoldProduct} entity.
     * Each campaign can have multiple sold products.
     */
    @OneToMany(mappedBy = "campaign")
    private List<SoldProduct> soldProducts;

}
