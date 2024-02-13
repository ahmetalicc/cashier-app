package org.sau.Toyota.Backend.kasiyerapp.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "price", length = 50, nullable = false)
    private Long price;
    @Column(name = "image")
    private byte[] image;
    @Column(name = "description", length = 1000, nullable = false)
    private String description;
    @Column(name = "brand", length = 50, nullable = false)
    private String brand;
    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;
    @Column(name = "stock", nullable = false)
    private int stock;
    @Column(name = "barcode", unique = true)
    private byte[] barcode;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "product")
    private List<SoldProduct> soldProducts;

}
