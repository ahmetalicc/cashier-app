package org.sau.toyota.backend.usermanagementservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.usermanagementservice.entity.User;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * DTO (Data Transfer Object) representing the response for an updated user.
 * Contains the updated user's name, username, and email.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedUserResponse {

    /**
     * The name of the updated user.
     */
    private String name;

    /**
     * The username of the updated user.
     */
    private String username;

    /**
     * The email of the updated user.
     */
    private String email;

    /**
     * Converts a User entity to an UpdatedUserResponse object.
     *
     * @param user The User entity to convert.
     * @return An UpdatedUserResponse object containing the user's information.
     */
    public static UpdatedUserResponse Convert(User user){
        return UpdatedUserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
