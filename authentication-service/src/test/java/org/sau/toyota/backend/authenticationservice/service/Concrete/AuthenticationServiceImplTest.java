package org.sau.toyota.backend.authenticationservice.service.Concrete;

import io.jsonwebtoken.JwtException;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
    @DisplayName("The test when user not found should throw RuntimeException")
    @Test
    void auth_whenUserNotFound_ShouldThrowRuntimeException() {
        UserLoginRequest userLoginRequest = new UserLoginRequest("non_existing_user", "password");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(()-> authenticationService.auth(userLoginRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @DisplayName("The test when authentication fails should throw RuntimeException")
    @Test
    void auth_whenAuthenticationFails_ShouldThrowRuntimeException() {
        UserLoginRequest userLoginRequest = new UserLoginRequest("test", "invalid_password");

        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Authentication failed"));

        assertThrows(RuntimeException.class, () -> authenticationService.auth(userLoginRequest));
        verify(authenticationManager, times(1)).authenticate(any());
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
    @DisplayName("The test when token is invalid should throw JwtException")
    @Test
    void isValid_whenTokenIsInvalid_ShouldThrowJwtException() {
        String invalidToken = "invalid_token";

        doThrow(JwtException.class).when(jwtService).validateToken(eq(invalidToken));

        assertThrows(JwtException.class, () -> authenticationService.isValid(invalidToken));
        verify(jwtService, times(1)).validateToken(eq(invalidToken));
}
}
