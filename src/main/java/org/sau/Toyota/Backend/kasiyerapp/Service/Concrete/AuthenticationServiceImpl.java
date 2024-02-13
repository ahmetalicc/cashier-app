package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dao.RoleRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dao.UserRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserLoginRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserRegisterRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Role;
import org.sau.Toyota.Backend.kasiyerapp.Entity.User;
import org.sau.Toyota.Backend.kasiyerapp.Security.JwtService;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final JwtService jwtService;
    @Override
    public TokenResponse save(UserRegisterRequest userRegisterRequest) {
        List<Role> roles = userRegisterRequest.getRoleId().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        User user = User.builder().name(userRegisterRequest.getName())
                        .username(userRegisterRequest.getUsername())
                                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                                        .email(userRegisterRequest.getEmail())
                                                .roles(roles).build();


        userRepository.save(user);

        var token = jwtService.generateToken(user);

        return TokenResponse.builder().token(token).build();
    }

    @Override
    public TokenResponse auth(UserLoginRequest userLoginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
        User user = userRepository.findByUsername(userLoginRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return TokenResponse.builder().token(token).build();
    }
}
