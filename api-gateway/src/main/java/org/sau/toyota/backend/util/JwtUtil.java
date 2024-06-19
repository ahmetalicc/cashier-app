package org.sau.toyota.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Utility class for handling JSON Web Tokens (JWT) in services.
 * This class provides methods to extract username, roles, and other claims from JWT tokens.
 */

@SuppressWarnings("ALL")
@Service
@Log4j2
public class JwtUtil {

    @Value("${toyota.security.jwt.secret}")
    private String SECRET_KEY;

    /**
     * Extracts the username from the JWT token.
     * @param token The JWT token from which to extract the username.
     * @return The username extracted from the token.
     * @throws Exception If the username cannot be extracted from the token.
     */
    public String getUsernameFromToken(String token) {
        try {
            return getClaimFromToken(token, Claims::getSubject);
        } catch (Exception UsernameNotFoundException) {
            log.warn("The username that is coming from token is invalid");
            throw UsernameNotFoundException;
        }
    }

    /**
     * Extracts a specific claim from the JWT token using the provided claims resolver function.
     * @param <T> The type of the claim value.
     * @param token The JWT token from which to extract the claim.
     * @param claimsResolver The function to resolve the claim from the token's claims.
     * @return The resolved claim value.
     * @throws Exception If the claim cannot be resolved from the token.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            log.warn("Claims couldn't get from the token with t function");
            throw e;
        }
    }

    /**
     * Parses and retrieves all claims from the JWT token.
     *
     * @param token The JWT token from which to retrieve claims.
     * @return The claims extracted from the token.
     * @throws Exception If the claims cannot be parsed from the token.
     */
    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.warn("Couldn't get the information from token with secret key");
            throw e;
        }
    }

    /**
     * Parses the JWT token to extract the list of roles claimed in the token.
     * @param token The JWT token from which to extract the roles.
     * @return The list of roles claimed in the token.
     * @throws Exception If the roles cannot be parsed from the token.
     */
    public List<String> parseTokenGetRoles(String token) {
        try {
            return (List<String>) Jwts
                    .parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("roles");
        } catch (Exception e) {
            log.warn(String.format("Token couldn't parsed in order to get the role claims. Token: %s ", token));
            throw e;
        }
    }
}
