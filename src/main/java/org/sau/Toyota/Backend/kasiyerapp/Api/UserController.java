package org.sau.Toyota.Backend.kasiyerapp.Api;

import lombok.RequiredArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.AddRemoveRoleRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserRegisterRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserUpdateRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.UpdatedUserResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.UserViewResponse;
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
    public ResponseEntity<List<UserViewResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "3") int size,
                                                              @RequestParam(defaultValue = "name") String sortBy,
                                                              @RequestParam(defaultValue = "asc") String sortOrder,
                                                              @RequestParam(required = false) String filter,
                                                              @RequestParam(defaultValue = "2") Integer isActive){
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortBy, sortOrder, filter, isActive));
    }

    @PostMapping("/addRole")
    public ResponseEntity<?> addRole(@RequestBody AddRemoveRoleRequest addRemoveRoleRequest){
        try {
            userService.addRole(addRemoveRoleRequest);
            return ResponseEntity.ok(String.format("Role is added with id: %s to the user with username: %s",
                    addRemoveRoleRequest.getRoleId(), addRemoveRoleRequest.getUsername()));
        }
        catch (IllegalStateException | NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<TokenResponse> saveUser(@RequestBody UserRegisterRequest userRegisterRequest){
        return ResponseEntity.ok(userService.saveUser(userRegisterRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UpdatedUserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        return ResponseEntity.ok(userService.updateUser(id, userUpdateRequest));
    }

    @DeleteMapping("/removeRole")
    public ResponseEntity<?> removeRole(@RequestBody AddRemoveRoleRequest addRemoveRoleRequest){
        try {
            userService.removeRole(addRemoveRoleRequest);
            return ResponseEntity.ok(String.format("Role is removed with id: %s from the user with username: %s",
                    addRemoveRoleRequest.getRoleId(), addRemoveRoleRequest.getUsername()));
        }
        catch (IllegalStateException | NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> softDeleteUser(@PathVariable Long id) {
        try {
            userService.softDeleteUser(id);
            return ResponseEntity.ok().build();
        } catch (NullPointerException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/activate/{id}")
    public ResponseEntity<Object> ActivateUser(@PathVariable Long id){
        try {
            userService.ActivateUser(id);
            return ResponseEntity.ok().build();
        }
        catch (NullPointerException | IllegalStateException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
