package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dao.UserRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserLoginRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.User;
import org.sau.Toyota.Backend.kasiyerapp.Security.JwtService;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    @Override
    public TokenResponse auth(UserLoginRequest userLoginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
        User user = userRepository.findByUsername(userLoginRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return TokenResponse.builder().token(token).build();
    }
}
