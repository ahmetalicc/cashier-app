package org.sau.toyota.backend.authenticationservice.service.Concrete;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.sau.toyota.backend.authenticationservice.dao.UserRepository;
import org.sau.toyota.backend.authenticationservice.dto.request.UserLoginRequest;
import org.sau.toyota.backend.authenticationservice.dto.response.TokenResponse;
import org.sau.toyota.backend.authenticationservice.entity.User;
import org.sau.toyota.backend.authenticationservice.security.JwtService;
import org.sau.toyota.backend.authenticationservice.service.Abstract.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    @Override
    public TokenResponse auth(UserLoginRequest userLoginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
            log.trace("User authenticated successfully: {}", userLoginRequest.getUsername());

            User user = userRepository.findByUsername(userLoginRequest.getUsername()).orElseThrow(() -> {
                log.error("User not found with username: {}", userLoginRequest.getUsername());
                return new RuntimeException("User not found");
            });
            log.trace("User found: {}", user);

            String token = jwtService.generateToken(user);
            log.trace("Token generated for user: {}", user);

            return TokenResponse.builder().token(token).build();
        } catch (RuntimeException e) {
            log.error("Error during authentication for user: {}", userLoginRequest.getUsername());
            throw e;
        }
    }

    @Override
    public void isValid(String token) {
        try {
            jwtService.validateToken(token);
            log.trace("Token is valid: {}", token);
        } catch (JwtException e) {
            log.warn("Invalid token: {}", token);
            throw e;
        }
    }

}
