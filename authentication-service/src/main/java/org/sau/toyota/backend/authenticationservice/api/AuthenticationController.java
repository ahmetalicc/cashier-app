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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public DataResult<TokenResponse> auth(@RequestBody UserLoginRequest userLoginRequest){
        return new SuccessDataResult<>(authenticationService.auth(userLoginRequest) ,"Login successful.");
    }

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
