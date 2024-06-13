package org.sau.toyota.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@SuppressWarnings("ALL")
@Service
@Log4j2
public class JwtUtil {

    @Value("${toyota.security.jwt.secret}")
    private String SECRET_KEY;

    public String getUsernameFromToken(String token) {
        try {
            return getClaimFromToken(token, Claims::getSubject);
        } catch (Exception UsernameNotFoundException) {
            log.warn("The username that is coming from token is invalid");
            throw UsernameNotFoundException;
        }
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            log.warn("Claims couldn't get from the token with t function");
            throw e;
        }
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.warn("Couldn't get the information from token with secret key");
            throw e;
        }
    }

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
