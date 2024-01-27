package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dao.RoleRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dao.UserRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserLoginDTO;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserRegister;
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
    public TokenResponse save(UserRegister userRegister) {
        List<Role> roles = userRegister.getRoleId().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        User user = User.builder().name(userRegister.getName())
                        .username(userRegister.getUsername())
                                .password(passwordEncoder.encode(userRegister.getPassword()))
                                        .email(userRegister.getEmail())
                                                .roles(roles).build();


        userRepository.save(user);

        var token = jwtService.generateToken(user);

        return TokenResponse.builder().token(token).build();
    }

    @Override
    public TokenResponse auth(UserLoginDTO userLoginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
        User user = userRepository.findByUsername(userLoginDTO.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return TokenResponse.builder().token(token).build();
    }
}
