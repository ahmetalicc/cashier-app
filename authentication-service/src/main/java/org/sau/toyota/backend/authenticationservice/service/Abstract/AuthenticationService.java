package org.sau.toyota.backend.authenticationservice.service.Abstract;


import org.sau.toyota.backend.authenticationservice.dto.request.UserLoginRequest;
import org.sau.toyota.backend.authenticationservice.dto.response.TokenResponse;

public interface AuthenticationService {
    TokenResponse auth(UserLoginRequest userLoginRequest);

    void isValid(String token);
}
