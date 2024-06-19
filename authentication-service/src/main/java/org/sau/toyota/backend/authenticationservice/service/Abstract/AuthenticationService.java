package org.sau.toyota.backend.authenticationservice.service.Abstract;


import org.sau.toyota.backend.authenticationservice.dto.request.UserLoginRequest;
import org.sau.toyota.backend.authenticationservice.dto.response.TokenResponse;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * This interface defines methods for user authentication and token validation.
 */
public interface AuthenticationService {
    /**
     * Authenticates a user based on the provided login request.
     *
     * @param userLoginRequest The login request containing user credentials.
     * @return TokenResponse object containing the JWT token upon successful authentication.
     */
    TokenResponse auth(UserLoginRequest userLoginRequest);

    /**
     * Validates the authenticity and expiration of a JWT token.
     *
     * @param token The JWT token to validate.
     */
    void isValid(String token);
}
