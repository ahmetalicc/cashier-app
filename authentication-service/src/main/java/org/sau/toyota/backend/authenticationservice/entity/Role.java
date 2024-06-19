package org.sau.toyota.backend.authenticationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Entity class representing a role.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roles")
public class Role {

    /**
     * The unique identifier for the Role entity.
     * The implementing strategy is defined as identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the role which is unique and not nullable.
     */
    @Column(name="role_name",nullable = false,unique = true)
    private String name;

    /**
     * The list of users associated with this role.
     * This field represents the many-to-many relationship between roles and users, using the {@link javax.persistence.ManyToMany} annotation.
     * CascadeType.ALL ensures that if a role is removed, associated users are also removed.
     * The mappedBy attribute specifies the field in the User entity that owns the relationship.
     */
    @ManyToMany(cascade = CascadeType.ALL
            ,mappedBy = "roles" )
    private List<User> users;

    /**
     * Overridden toString method for Role entity.
     * @return A string representation of the Role object.
     */
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
