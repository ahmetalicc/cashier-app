package org.sau.toyota.backend.usermanagementservice.service.Abstract;


import org.sau.toyota.backend.usermanagementservice.dto.request.AddRemoveRoleRequest;
import org.sau.toyota.backend.usermanagementservice.dto.request.UserRegisterRequest;
import org.sau.toyota.backend.usermanagementservice.dto.request.UserUpdateRequest;
import org.sau.toyota.backend.usermanagementservice.dto.response.TokenResponse;
import org.sau.toyota.backend.usermanagementservice.dto.response.UpdatedUserResponse;
import org.sau.toyota.backend.usermanagementservice.dto.response.UserViewResponse;

import java.util.List;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Service interface for managing user-related operations.
 */
public interface UserService {
    /**
     * Retrieves a paginated list of users with optional sorting and filtering.
     *
     * @param page       The page number to retrieve.
     * @param size       The number of users per page.
     * @param sortBy     The field to sort by.
     * @param sortOrder  The order of sorting, either "asc" or "desc".
     * @param filter     The filter to apply to the username.
     * @param isActive   The filter to apply to the user's active status. 1 for active, 0 for inactive, 2 for all.
     * @return A list of UserViewResponse objects.
     */
    List<UserViewResponse> getAllUsers(int page, int size, String sortBy, String sortOrder, String filter, Integer isActive);
    /**
     * Registers a new user and returns a token response.
     *
     * @param userRegisterRequest The request object containing user registration details.
     * @return A TokenResponse object containing the generated JWT token.
     */
    TokenResponse saveUser(UserRegisterRequest userRegisterRequest);
    /**
     * Updates an existing user's information.
     *
     * @param id                The ID of the user to update.
     * @param userUpdateRequest The request object containing updated user details.
     * @return An UpdatedUserResponse object containing the updated user information.
     */
    UpdatedUserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);
    /**
     * Soft deletes a user by deactivating their account.
     *
     * @param id The ID of the user to deactivate.
     */
    void softDeleteUser(Long id);
    /**
     * Activates a previously deactivated user account.
     *
     * @param id The ID of the user to activate.
     */
    void ActivateUser(Long id);
    /**
     * Adds a role to a user.
     *
     * @param addRemoveRoleRequest The request object containing the username and role ID.
     */
    void addRole(AddRemoveRoleRequest addRemoveRoleRequest);
    /**
     * Removes a role from a user.
     *
     * @param addRemoveRoleRequest The request object containing the username and role ID.
     */
    void removeRole(AddRemoveRoleRequest addRemoveRoleRequest);
}
