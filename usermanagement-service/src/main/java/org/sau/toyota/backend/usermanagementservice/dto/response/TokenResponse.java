package org.sau.toyota.backend.usermanagementservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * DTO (Data Transfer Object) representing the response containing a token.
 * Contains the JWT token generated for authentication purposes.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    /**
     * The JWT token generated for authentication.
     */
    private String token;
}
