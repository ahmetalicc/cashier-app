package org.sau.toyota.backend.authenticationservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.sau.toyota.backend.authenticationservice.entity.Role;
import org.sau.toyota.backend.authenticationservice.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Service class responsible for handling JSON Web Token (JWT) operations.
 * <p>
 * This class manages token generation, token validation, and extracting user information from tokens.
 * It utilizes the io.jsonwebtoken library for JWT handling.
 * <p>
 * This service is used in the authentication process to manage user authentication tokens.
 */
@Service
public class JwtService {

    /**
     * The secret key used for JWT signing and validation.
     */
    @Value("${toyota.security.jwt.secret}")
    private String SECRET_KEY;

    /**
     * The expiration time for JWT tokens, specified in seconds.
     */
    @Value("${toyota.security.jwt.expire}")
    private Long EXPIRE_TIME;
    /**
     * Finds the username from a given JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username extracted from the token.
     */
    public String findUsername(String token) {
        return exportToken(token, Claims::getSubject);
    }
    /**
     * Extracts a specific claim from a JWT token using a provided function.
     *
     * @param token          The JWT token from which to extract the claim.
     * @param claimsTFunction The function to extract the claim from the token.
     * @return The extracted claim value.
     */
    private <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build().parseClaimsJws(token).getBody();

        return claimsTFunction.apply(claims);
    }
    /**
     * Retrieves the secret key used for JWT signing and validation.
     *
     * @return The secret key for JWT operations.
     */
    private Key getKey() {
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    /**
     * Validates whether a given JWT token is valid for a UserDetails object.
     *
     * @param jwt          The JWT token to validate.
     * @param userDetails  The UserDetails object representing the user.
     * @return True if the token is valid for the user, false otherwise.
     */
    public boolean tokenControl(String jwt, UserDetails userDetails) {
        final String username = findUsername(jwt);
        return (username.equals(userDetails.getUsername())
                && !exportToken(jwt, Claims::getExpiration).before(new Date()));
    }
    /**
     * Validates a JWT token without UserDetails checking.
     *
     * @param token The JWT token to validate.
     */
    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
    }
    /**
     * Generates a new JWT token for the given user.
     * <p>
     * This method creates a JWT token containing the user's username and role information as additional claims.
     * The expiration time for the token is set based on the configured expiration time property.
     * The token is signed using the secret key defined in the application properties.
     * <p>
     * @param user The user object for which to generate the token.
     * @return The generated JWT token.
     */
    public String generateToken(User user) {
        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("roles", user.getRoles().stream().map(Role::getName).toList());
        return Jwts.builder()
                .setClaims(additionalClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }
}
