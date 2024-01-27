package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserLoginDTO;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserRegister;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.User;
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
    public ResponseEntity<TokenResponse> save(@RequestBody UserRegister userRegister){
        return ResponseEntity.ok(authenticationService.save(userRegister));
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenResponse> auth(@RequestBody UserLoginDTO userLoginDTO){
        return ResponseEntity.ok(authenticationService.auth(userLoginDTO));
    }
}
