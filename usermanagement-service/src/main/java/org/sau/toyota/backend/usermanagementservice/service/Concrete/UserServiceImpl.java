package org.sau.toyota.backend.usermanagementservice.service.Concrete;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.sau.toyota.backend.usermanagementservice.dao.RoleRepository;
import org.sau.toyota.backend.usermanagementservice.dao.UserRepository;
import org.sau.toyota.backend.usermanagementservice.dto.request.AddRemoveRoleRequest;
import org.sau.toyota.backend.usermanagementservice.dto.request.UserRegisterRequest;
import org.sau.toyota.backend.usermanagementservice.dto.request.UserUpdateRequest;
import org.sau.toyota.backend.usermanagementservice.dto.response.TokenResponse;
import org.sau.toyota.backend.usermanagementservice.dto.response.UpdatedUserResponse;
import org.sau.toyota.backend.usermanagementservice.dto.response.UserViewResponse;
import org.sau.toyota.backend.usermanagementservice.entity.Role;
import org.sau.toyota.backend.usermanagementservice.entity.User;
import org.sau.toyota.backend.usermanagementservice.security.JwtService;
import org.sau.toyota.backend.usermanagementservice.service.Abstract.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public List<UserViewResponse> getAllUsers(int page, int size, String sortBy, String sortOrder, String filter, Integer isActive) {
        Sort sort = Sort.by(sortBy).ascending();
        if ("desc".equals(sortOrder)) {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users;
        if(filter != null && !filter.isEmpty()){
            users = userRepository.findUsersByUsername(filter, pageable);
        }
        else if(isActive.equals(1)){
                users = userRepository.findByActivenessIs(true, pageable);
            } else if (isActive.equals(0)) {
                users = userRepository.findByActivenessIs(false, pageable);
            }
            else {
                users = userRepository.findAll(pageable);
            }


        return users.stream()
                .map(UserViewResponse::Convert)
                .collect(Collectors.toList());
    }

    @Override
    public void addRole(AddRemoveRoleRequest addRemoveRoleRequest) {
        User user = userRepository.findByUsername(addRemoveRoleRequest.getUsername()).orElseThrow(
                ()-> new NullPointerException(String.format("User not found with username: %s ", addRemoveRoleRequest.getUsername())));
        Role role = roleRepository.findById(addRemoveRoleRequest.getRoleId()).orElseThrow(
                ()-> new NullPointerException(String.format("Role not found with id: %s", addRemoveRoleRequest.getRoleId())));
        List<Role> roles = user.getRoles();
        if(roles.contains(role)){
            log.info(String.format("User already has the role with id: %s", role.getId()));
            throw new IllegalStateException(String.format("User already has the role with id: %s", role.getId()));
        }
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        log.info(String.format("User %s is saved to the database with added new role with id: %s",
                addRemoveRoleRequest.getUsername(), addRemoveRoleRequest.getRoleId()));

    }
    @Override
    public TokenResponse saveUser(UserRegisterRequest userRegisterRequest) {
        List<Role> roles = userRegisterRequest.getRoleId().stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        User user = User.builder().name(userRegisterRequest.getName())
                .username(userRegisterRequest.getUsername())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .email(userRegisterRequest.getEmail())
                .roles(roles).build();

        userRepository.save(user);
        log.info(String.format("User %s is saved successfully to the database.", userRegisterRequest.getUsername()));

        var token = jwtService.generateToken(user);

        return TokenResponse.builder().token(token).build();
    }

    @Override
    public UpdatedUserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("User not found with id(%s) that is provided:", id)));

            if(userRepository.existsByUsername(userUpdateRequest.getUsername()) || userRepository.existsByEmail(userUpdateRequest.getEmail())){
                log.info(String.format("username(%s) or email(%s) that you want to update is already in the database.",
                        userUpdateRequest.getUsername(), userUpdateRequest.getEmail()));
                throw new IllegalStateException(String.format("username(%s) or email(%s) that you want to update is already in the database.",
                        userUpdateRequest.getUsername(), userUpdateRequest.getEmail()));
            }

        user.setName(userUpdateRequest.getName());
        user.setUsername(userUpdateRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        user.setEmail(userUpdateRequest.getEmail());

        userRepository.save(user);
        log.info(String.format("User information's are updated. New username is: %s and new email is: %s",
                userUpdateRequest.getUsername(), userUpdateRequest.getEmail()));
        return UpdatedUserResponse.Convert(user);
    }

    @Override
    public void removeRole(AddRemoveRoleRequest addRemoveRoleRequest) {
        User user = userRepository.findByUsername(addRemoveRoleRequest.getUsername()).orElseThrow(
                ()-> new NullPointerException(String.format("User not found with username: %s ", addRemoveRoleRequest.getUsername())));
        Role role = roleRepository.findById(addRemoveRoleRequest.getRoleId()).orElseThrow(
                ()-> new NullPointerException(String.format("Role not found with id: %s", addRemoveRoleRequest.getRoleId())));
        List<Role> roles = user.getRoles();
        if(!roles.contains(role)){
            log.info(String.format("User already has not the role with id: %s",role.getId()));
            throw new IllegalStateException(String.format("User already has not the role with id: %s",role.getId()));
        }
        if(roles.size() == 1){
            log.info("User must have at least one role");
            throw new IllegalStateException("User must have at least one role");
        }
        roles.remove(role);
        user.setRoles(roles);
        userRepository.save(user);
        log.info(String.format("User's role with id: %s is removed and user %s is saved to the database.",
                addRemoveRoleRequest.getRoleId(), addRemoveRoleRequest.getUsername()));
    }
    @Override
    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("User not found with id(%s) that is provided:", id)));

        if(!user.isActiveness()){
            log.info(String.format("User %s is already inactive.", user.getUsername()));
            throw new IllegalStateException(String.format("User %s is already inactive.", user.getUsername()));
        }

        user.setActiveness(false);
        userRepository.save(user);
        log.info(String.format("User %s is deactivated successfully. ", user.getUsername()));
    }

    @Override
    public void ActivateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("User not found with id(%s) that is provided:", id)));

        if(user.isActiveness()){
            log.info(String.format("User %s is already active.", user.getUsername()));
            throw new IllegalStateException(String.format("User %s is already active.", user.getUsername()));
        }

        user.setActiveness(true);
        userRepository.save(user);
        log.info(String.format("User %s is activated successfully.", user.getUsername()));
    }

}
