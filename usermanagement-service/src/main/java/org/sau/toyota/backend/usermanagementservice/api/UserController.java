package org.sau.toyota.backend.usermanagementservice.api;

import lombok.RequiredArgsConstructor;
import org.sau.toyota.backend.usermanagementservice.core.results.*;
import org.sau.toyota.backend.usermanagementservice.dto.request.AddRemoveRoleRequest;
import org.sau.toyota.backend.usermanagementservice.dto.request.UserRegisterRequest;
import org.sau.toyota.backend.usermanagementservice.dto.request.UserUpdateRequest;
import org.sau.toyota.backend.usermanagementservice.dto.response.UpdatedUserResponse;
import org.sau.toyota.backend.usermanagementservice.dto.response.UserViewResponse;
import org.sau.toyota.backend.usermanagementservice.service.Abstract.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * REST controller for handling user-related operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * Retrieves a list of users with optional pagination and sorting.
     *
     * @param page      Page number for pagination.
     * @param size      Number of items per page.
     * @param sortBy    Field to sort the results by.
     * @param sortOrder Sorting order (asc or desc).
     * @param filter    Optional filter parameter.
     * @param isActive  Active status filter (1 for active, 0 for inactive).
     * @return A DataResult containing a list of UserViewResponse objects.
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<DataResult<List<UserViewResponse>>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "3") int size,
                                                                         @RequestParam(defaultValue = "name") String sortBy,
                                                                         @RequestParam(defaultValue = "asc") String sortOrder,
                                                                         @RequestParam(required = false) String filter,
                                                                         @RequestParam(defaultValue = "2") Integer isActive){
        return ResponseEntity.ok(new SuccessDataResult<>
                (userService.getAllUsers(page, size, sortBy, sortOrder, filter, isActive),
                        "Data has been listed successfully."));
    }
    /**
     * Adds a role to a user.
     *
     * @param addRemoveRoleRequest The request containing the role ID and username.
     * @return A Result indicating the success or failure of the operation.
     */
    @PostMapping("/addRole")
    public ResponseEntity<Result> addRole(@RequestBody AddRemoveRoleRequest addRemoveRoleRequest){
        try {
            userService.addRole(addRemoveRoleRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResult(String.format("Role is added with id: %s to the user with username: %s",
                    addRemoveRoleRequest.getRoleId(), addRemoveRoleRequest.getUsername())));
        }
        catch (IllegalStateException | NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResult(e.getMessage()));
        }
    }
    /**
     * Registers a new user.
     *
     * @param userRegisterRequest The request containing user registration data.
     * @return A DataResult containing a UserViewResponse object and new user is added to database successfully message.
     */
    @PostMapping("/save")
    public ResponseEntity<DataResult<UserViewResponse>> saveUser(@RequestBody UserRegisterRequest userRegisterRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessDataResult<>(userService.saveUser(userRegisterRequest), "New user is added to database successfully."));
    }
    /**
     * Updates an existing user.
     *
     * @param id                 The ID of the user to update.
     * @param userUpdateRequest  The request containing user update data.
     * @return A DataResult containing an UpdatedUserResponse object if the update is successful,
     * or an ErrorDataResult containing an error message if the update fails.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<DataResult<UpdatedUserResponse>> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessDataResult<>(userService.updateUser(id, userUpdateRequest), "User updated successfully."));
        }
        catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDataResult<>(e.getMessage()));
        }
    }
    /**
     * Removes a role from a user.
     *
     * @param addRemoveRoleRequest The request containing the role ID and username.
     * @return A Result indicating the success or failure of the operation.
     */
    @DeleteMapping("/removeRole")
    public ResponseEntity<Result> removeRole(@RequestBody AddRemoveRoleRequest addRemoveRoleRequest){
        try {
            userService.removeRole(addRemoveRoleRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResult(String.format("Role is removed with id: %s from the user with username: %s",
                    addRemoveRoleRequest.getRoleId(), addRemoveRoleRequest.getUsername())));
        }
        catch (IllegalStateException | NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorResult(e.getMessage()));
        }
    }
    /**
     * Soft deletes a user (deactivates).
     *
     * @param id The ID of the user to soft-delete.
     * @return A Result indicating the success or failure of the operation.
     */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Result> softDeleteUser(@PathVariable Long id) {
        try {
            userService.softDeleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResult("User deactivated successfully."));
        } catch (NullPointerException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResult(e.getMessage()));
        }
    }
    /**
     * Activates a previously deactivated user.
     *
     * @param id The ID of the user to activate.
     * @return A Result indicating the success or failure of the operation.
     */
    @GetMapping("/activate/{id}")
    public ResponseEntity<Result> ActivateUser(@PathVariable Long id){
        try {
            userService.ActivateUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResult("User activated successfully."));
        }
        catch (NullPointerException | IllegalStateException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResult(e.getMessage()));
        }
    }


}
