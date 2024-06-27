package org.sau.toyota.backend.authenticationservice.api;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.sau.toyota.backend.authenticationservice.core.results.*;
import org.sau.toyota.backend.authenticationservice.dto.request.UserLoginRequest;
import org.sau.toyota.backend.authenticationservice.dto.response.TokenResponse;
import org.sau.toyota.backend.authenticationservice.service.Abstract.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * AuthenticationController is responsible for handling authentication-related API requests.
 * This controller provides endpoints for user login and token validation.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Handles user login requests.
     *
     * @param userLoginRequest the login request containing username and password
     * @return a SuccessDataResult containing a TokenResponse if login is successful, with a success message
     */
    @PostMapping("/login")
    public DataResult<TokenResponse> auth(@RequestBody UserLoginRequest userLoginRequest){
        try {
            return new SuccessDataResult<>(authenticationService.auth(userLoginRequest), "Login successful.");
        } catch (RuntimeException e){
            return new ErrorDataResult<>(e.getMessage());
        }
    }

    /**
     * Validates a JWT token provided in the Authorization header.
     *
     * @param authHeader the Authorization header containing the Bearer token
     * @return a ResponseEntity containing a SuccessResult if the token is valid, or an ErrorResult if invalid
     */
    @GetMapping("/validate")
    public ResponseEntity<Result> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        try {
            var token = authHeader.substring(7);
            authenticationService.isValid(token);
            return new ResponseEntity<>(new SuccessResult("Token is valid."), HttpStatus.OK);
        }
        catch (JwtException e){
            return new ResponseEntity<>(new ErrorResult("Token is not valid!"), HttpStatus.UNAUTHORIZED);
        }
    }
}
