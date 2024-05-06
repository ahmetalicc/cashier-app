package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sau.Toyota.Backend.kasiyerapp.Dao.UserRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserLoginRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.User;
import org.sau.Toyota.Backend.kasiyerapp.Fixture.UserFixture;
import org.sau.Toyota.Backend.kasiyerapp.Security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @DisplayName("The test when call with valid user information that should return JWT Token with TokenResponse")
    @Test
    void auth_whenCallWithValidUserInformation_ShouldReturnJwtTokenWithTokenResponse(){
        UserLoginRequest userLoginRequest = new UserLoginRequest("test", "password");
        UserFixture userFixture = new UserFixture();
        User user = userFixture.createUser();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("mocked_token");

        TokenResponse tokenResponse = authenticationService.auth(userLoginRequest);

        assertNotNull(tokenResponse);
        assertEquals("mocked_token", tokenResponse.getToken());
        verify(userRepository, times(1)).findByUsername(anyString());
    }

}
