package org.sau.Toyota.Backend.kasiyerapp.Service.Abstract;

import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserLoginRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserRegisterRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;

public interface AuthenticationService {
    TokenResponse save(UserRegisterRequest userRegisterRequest);

    TokenResponse auth(UserLoginRequest userLoginRequest);
}
