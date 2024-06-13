package org.sau.toyota.backend.usermanagementservice.service.Abstract;


import org.sau.toyota.backend.usermanagementservice.dto.request.AddRemoveRoleRequest;
import org.sau.toyota.backend.usermanagementservice.dto.request.UserRegisterRequest;
import org.sau.toyota.backend.usermanagementservice.dto.request.UserUpdateRequest;
import org.sau.toyota.backend.usermanagementservice.dto.response.TokenResponse;
import org.sau.toyota.backend.usermanagementservice.dto.response.UpdatedUserResponse;
import org.sau.toyota.backend.usermanagementservice.dto.response.UserViewResponse;

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
