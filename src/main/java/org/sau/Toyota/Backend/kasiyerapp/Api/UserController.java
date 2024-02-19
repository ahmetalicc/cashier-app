package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Core.Utils.Results.*;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.AddRemoveRoleRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserRegisterRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserUpdateRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.UpdatedUserResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.UserViewResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.User;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/getAllUsers")
    public DataResult<List<UserViewResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size,
                                                          @RequestParam(defaultValue = "name") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder,
                                                          @RequestParam(required = false) String filter,
                                                          @RequestParam(defaultValue = "2") Integer isActive){
        return new SuccessDataResult<>
                (userService.getAllUsers(page, size, sortBy, sortOrder, filter, isActive),
                        "Data has been listed.");
    }

    @PostMapping("/addRole")
    public Result addRole(@RequestBody AddRemoveRoleRequest addRemoveRoleRequest){
        try {
            userService.addRole(addRemoveRoleRequest);
            return new SuccessResult(String.format("Role is added with id: %s to the user with username: %s",
                    addRemoveRoleRequest.getRoleId(), addRemoveRoleRequest.getUsername()));
        }
        catch (IllegalStateException | NullPointerException e){
            return new ErrorResult(e.getMessage());
        }
    }

    @PostMapping("/save")
    public DataResult<TokenResponse> saveUser(@RequestBody UserRegisterRequest userRegisterRequest){
        return new SuccessDataResult<>(userService.saveUser(userRegisterRequest), "Registered successfully.");
    }

    @PutMapping("/update/{id}")
    public DataResult<UpdatedUserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        return new SuccessDataResult<>(userService.updateUser(id, userUpdateRequest), "User updated successfully.");
    }

    @DeleteMapping("/removeRole")
    public Result removeRole(@RequestBody AddRemoveRoleRequest addRemoveRoleRequest){
        try {
            userService.removeRole(addRemoveRoleRequest);
            return new SuccessResult(String.format("Role is removed with id: %s from the user with username: %s",
                    addRemoveRoleRequest.getRoleId(), addRemoveRoleRequest.getUsername()));
        }
        catch (IllegalStateException | NullPointerException e){
            return new ErrorResult(e.getMessage());
        }
    }

    @GetMapping("/delete/{id}")
    public Result softDeleteUser(@PathVariable Long id) {
        try {
            userService.softDeleteUser(id);
            return new SuccessResult("User deactivated successfully.");
        } catch (NullPointerException | IllegalStateException e) {
            return new ErrorResult(e.getMessage());
        }
    }


    @GetMapping("/activate/{id}")
    public Result ActivateUser(@PathVariable Long id){
        try {
            userService.ActivateUser(id);
            return new SuccessResult("User activated successfully.");
        }
        catch (NullPointerException | IllegalStateException e){
           return new ErrorResult(e.getMessage());
        }
    }


}
