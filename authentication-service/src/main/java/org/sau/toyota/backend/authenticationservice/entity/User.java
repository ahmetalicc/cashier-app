package org.sau.toyota.backend.authenticationservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Entity class representing a user.
 * This class defines the user entities used in the application.
 * It contains information about user ID, email, name, username, password, activeness, roles, and authorities.
 * Implements UserDetails interface for Spring Security integration.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    /**
     * The unique identifier for the User entity.
     * The implementing strategy is defined as identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The email address of the user which is unique not nullable and can have a maximum length of 50 characters.
     */
    @Column(name="email",nullable = false,length = 50,unique = true)
    private String email;
    /**
     * The name of the user which not nullable and can have a maximum length of 50 characters.
     */
    @Column(name="name",nullable = false,length = 50)
    private String name;
    /**
     * The username of the user which is unique not nullable and can have a maximum length of 50 characters.
     */
    @Column(name="user_name",nullable = false,length = 50,unique = true)
    private String username;
    /**
     * The activeness status of the user.
     * This field indicates whether the user account is active or not.
     */
    @Column(name="activeness")
    private boolean activeness;
    /**
     * The password of the User, which is not nullable and can have a maximum length of 100 characters.
     */
    @Column(name="password",nullable = false,length = 100,unique = true)
    private String password;

    /**
     * The roles associated with the user.
     * This attribute establishes a many-to-many relationship with the {@link Role} entity.
     * It is mapped by the users attribute in the {@code Role} entity.
     * The {@link javax.persistence.CascadeType#ALL} property ensures that changes made to a role are propagated to all users having that role.
     * The {@link javax.persistence.FetchType#EAGER} property specifies that roles should be eagerly loaded during user retrieval.
     */
    @ManyToMany(cascade = CascadeType.ALL
            , fetch = FetchType.EAGER)
    @JoinTable(name = "user_role"
            , joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)}
            , inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    @JsonIgnore
    private List<Role> roles;
}
