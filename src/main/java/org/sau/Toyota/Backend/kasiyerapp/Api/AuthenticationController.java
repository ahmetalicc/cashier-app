package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserLoginRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserRegisterRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/save")
    public ResponseEntity<TokenResponse> save(@RequestBody UserRegisterRequest userRegisterRequest){
        return ResponseEntity.ok(authenticationService.save(userRegisterRequest));
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenResponse> auth(@RequestBody UserLoginRequest userLoginRequest){
        return ResponseEntity.ok(authenticationService.auth(userLoginRequest));
    }
}
