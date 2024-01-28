package org.sau.Toyota.Backend.kasiyerapp.Service.Abstract;

import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserLoginDTO;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserRegister;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;

public interface AuthenticationService {
    TokenResponse save(UserRegister userRequest);

    TokenResponse auth(UserLoginDTO userLoginDTO);
}
