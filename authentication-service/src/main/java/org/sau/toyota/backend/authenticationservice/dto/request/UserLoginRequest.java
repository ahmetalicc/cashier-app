package org.sau.toyota.backend.authenticationservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * DTO representing a user login request.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;


}
