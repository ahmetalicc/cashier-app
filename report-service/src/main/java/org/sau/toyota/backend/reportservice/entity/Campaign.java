package org.sau.toyota.backend.reportservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "campaigns")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 1000, nullable = false)
    private String description;


    @ManyToOne()
    @JoinColumn( name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "campaign")
    private List<SoldProduct> soldProducts;

}
