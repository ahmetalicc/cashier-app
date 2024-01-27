package org.sau.Toyota.Backend.kasiyerapp.Entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name="role_name",nullable = false,unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL
            ,mappedBy = "roles" )
    private List<User> users;
}
