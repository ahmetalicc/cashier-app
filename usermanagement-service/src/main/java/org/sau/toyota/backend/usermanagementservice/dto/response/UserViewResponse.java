package org.sau.toyota.backend.usermanagementservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.usermanagementservice.entity.Role;
import org.sau.toyota.backend.usermanagementservice.entity.User;

import java.util.List;
import java.util.stream.Collectors;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * DTO (Data Transfer Object) representing the response for viewing a user's details.
 * Contains the user's name, username, email, activation status, and roles.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserViewResponse {

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The activation status of the user.
     */
    private boolean is_active;

    /**
     * The roles assigned to the user.
     */
    private List<String> roles;

    /**
     * Converts a User entity to a UserViewResponse object.
     *
     * @param user The User entity to convert.
     * @return A UserViewResponse object containing the user's information.
     */
    public static UserViewResponse Convert(User user){
        return UserViewResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .is_active(user.isActiveness())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }
}
