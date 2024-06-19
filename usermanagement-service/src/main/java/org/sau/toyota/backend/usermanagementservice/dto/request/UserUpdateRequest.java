package org.sau.toyota.backend.usermanagementservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * DTO (Data Transfer Object) representing the request for updating user information.
 * Contains the updated user's name, username, password, and email.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {

    /**
     * The updated name of the user.
     */
    private String name;

    /**
     * The updated username of the user.
     */
    private String username;

    /**
     * The updated password of the user.
     */
    private String password;

    /**
     * The updated email address of the user.
     */
    private String email;
}
