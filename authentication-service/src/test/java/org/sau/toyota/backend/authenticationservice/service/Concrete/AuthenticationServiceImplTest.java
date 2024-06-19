package org.sau.toyota.backend.authenticationservice.service.Concrete;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sau.toyota.backend.authenticationservice.fixture.UserFixture;
import org.sau.toyota.backend.authenticationservice.dao.UserRepository;
import org.sau.toyota.backend.authenticationservice.dto.request.UserLoginRequest;
import org.sau.toyota.backend.authenticationservice.dto.response.TokenResponse;
import org.sau.toyota.backend.authenticationservice.entity.User;
import org.sau.toyota.backend.authenticationservice.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;

import java.security.Key;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** @author Ahmet Alıç
 * @since 14-06-2024
 * Unit tests for {@link AuthenticationServiceImpl} to verify the functionality of authentication methods.
 */
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Key key;
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

    @DisplayName("The test when token is valid should return a string 'Token is valid.'")
    @Test
    void isValid_whenTheTokenIsValid_ShouldReturnString(){
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String validToken = Jwts.builder()
                .setSubject("testUser")
                .signWith(key)
                .compact();

        assertDoesNotThrow(() -> authenticationService.isValid(validToken));

    }
}
