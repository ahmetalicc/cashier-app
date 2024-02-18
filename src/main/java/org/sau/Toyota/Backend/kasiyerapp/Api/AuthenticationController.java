package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserLoginRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/auth")
    public ResponseEntity<TokenResponse> auth(@RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity.ok(authenticationService.auth(userLoginRequest));
    }
}
