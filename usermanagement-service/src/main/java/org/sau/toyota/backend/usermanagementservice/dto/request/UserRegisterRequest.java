package org.sau.toyota.backend.usermanagementservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * DTO (Data Transfer Object) representing the request for user registration.
 * Contains the user's name, username, password, email, and a list of role IDs associated with the user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The list of role IDs associated with the user.
     */
    private List<Long> RoleId;
}
