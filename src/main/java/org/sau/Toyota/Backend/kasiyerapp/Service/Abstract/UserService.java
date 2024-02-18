package org.sau.Toyota.Backend.kasiyerapp.Service.Abstract;

import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.AddRemoveRoleRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserRegisterRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserUpdateRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.UpdatedUserResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.UserViewResponse;

import java.util.List;

public interface UserService {

    List<UserViewResponse> getAllUsers(int page, int size, String sortBy, String sortOrder, String filter, Integer isActive);
    TokenResponse saveUser(UserRegisterRequest userRegisterRequest);

    UpdatedUserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);

    void softDeleteUser(Long id);

    void ActivateUser(Long id);

    void addRole(AddRemoveRoleRequest addRemoveRoleRequest);

    void removeRole(AddRemoveRoleRequest addRemoveRoleRequest);
}
