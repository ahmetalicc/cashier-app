package org.sau.Toyota.Backend.kasiyerapp.Service.Concrete;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.sau.Toyota.Backend.kasiyerapp.Dao.RoleRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dao.UserRepository;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.AddRemoveRoleRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserRegisterRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Request.UserUpdateRequest;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.TokenResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.UpdatedUserResponse;
import org.sau.Toyota.Backend.kasiyerapp.Dto.Response.UserViewResponse;
import org.sau.Toyota.Backend.kasiyerapp.Entity.Role;
import org.sau.Toyota.Backend.kasiyerapp.Entity.User;
import org.sau.Toyota.Backend.kasiyerapp.Security.JwtService;
import org.sau.Toyota.Backend.kasiyerapp.Service.Abstract.UserService;
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
public class UserServiceImpl implements UserService {

    private static final Logger logger =  Logger.getLogger(UserServiceImpl.class);

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
            logger.info(String.format("User already has the role with id: %s", role.getId()));
            throw new IllegalStateException(String.format("User already has the role with id: %s", role.getId()));
        }
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        logger.info(String.format("User %s is saved to the database with added new role with id: %s",
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
        logger.info(String.format("User %s is saved successfully to the database.", userRegisterRequest.getUsername()));

        var token = jwtService.generateToken(user);

        return TokenResponse.builder().token(token).build();
    }

    @Override
    public UpdatedUserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("User not found with id(%s) that is provided:", id)));

            if(userRepository.existsByUsername(userUpdateRequest.getUsername()) || userRepository.existsByEmail(userUpdateRequest.getEmail())){
                logger.info(String.format("username(%s) or email(%s) that you want to update is already in the database.",
                        userUpdateRequest.getUsername(), userUpdateRequest.getEmail()));
                throw new IllegalStateException(String.format("username(%s) or email(%s) that you want to update is already in the database.",
                        userUpdateRequest.getUsername(), userUpdateRequest.getEmail()));
            }

        user.setName(userUpdateRequest.getName());
        user.setUsername(userUpdateRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        user.setEmail(userUpdateRequest.getEmail());

        userRepository.save(user);
        logger.info(String.format("User information's are updated. New username is: %s and new email is: %s",
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
            logger.info(String.format("User already has not the role with id: %s",role.getId()));
            throw new IllegalStateException(String.format("User already has not the role with id: %s",role.getId()));
        }
        if(roles.size() == 1){
            logger.info("User must have at least one role");
            throw new IllegalStateException("User must have at least one role");
        }
        roles.remove(role);
        user.setRoles(roles);
        userRepository.save(user);
        logger.info(String.format("User's role with id: %s is removed and user %s is saved to the database.",
                addRemoveRoleRequest.getRoleId(), addRemoveRoleRequest.getUsername()));
    }
    @Override
    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("User not found with id(%s) that is provided:", id)));

        if(!user.isActiveness()){
            logger.info(String.format("User %s is already inactive.", user.getUsername()));
            throw new IllegalStateException(String.format("User %s is already inactive.", user.getUsername()));
        }

        user.setActiveness(false);
        userRepository.save(user);
        logger.info(String.format("User %s is deactivated successfully. ", user.getUsername()));
    }

    @Override
    public void ActivateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new NullPointerException(String.format("User not found with id(%s) that is provided:", id)));

        if(user.isActiveness()){
            logger.info(String.format("User %s is already active.", user.getUsername()));
            throw new IllegalStateException(String.format("User %s is already active.", user.getUsername()));
        }

        user.setActiveness(true);
        userRepository.save(user);
        logger.info(String.format("User %s is activated successfully.", user.getUsername()));
    }

}
