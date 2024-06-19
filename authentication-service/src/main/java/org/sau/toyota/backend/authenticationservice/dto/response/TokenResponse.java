package org.sau.toyota.backend.authenticationservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * DTO representing a token response.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    /**
     * The JWT token.
     */
    private String token;
}
