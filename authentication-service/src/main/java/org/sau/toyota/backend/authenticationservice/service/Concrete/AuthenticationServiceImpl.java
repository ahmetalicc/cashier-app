package org.sau.toyota.backend.authenticationservice.service.Concrete;

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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
        User user = userRepository.findByUsername(userLoginRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return TokenResponse.builder().token(token).build();
    }

    @Override
    public void isValid(String token) {
        jwtService.validateToken(token);
    }

}
