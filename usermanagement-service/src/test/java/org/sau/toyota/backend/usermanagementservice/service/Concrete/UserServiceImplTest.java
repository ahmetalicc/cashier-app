package org.sau.toyota.backend.usermanagementservice.service.Concrete;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import org.sau.toyota.backend.usermanagementservice.fixture.RoleFixture;
import org.sau.toyota.backend.usermanagementservice.fixture.UserFixture;
import org.sau.toyota.backend.usermanagementservice.security.JwtService;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
/** @author Ahmet Alıç
 * @since 15-06-2024
 * Unit tests for {@link UserServiceImpl} class.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private Logger logger;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private UserServiceImpl userService;


    @DisplayName("The test that when call with page size sortBy sortOrder and filter should return list of UserViewResponse list with pagination")
    @ParameterizedTest
    @ValueSource(strings = {"test", "asd", "123", "ahmet"})
    void getAllUsers_whenCallWithFilter_ShouldReturnUserViewResponseListWithPagination(String filter){
        UserFixture userFixture = new UserFixture();
        List<User> userList = userFixture.createUserList();
        Page<User> pagedUsers = new PageImpl<>(userList);

        when(userRepository.findUsersByUsername(eq(filter), any(Pageable.class))).thenReturn(pagedUsers);

        List<UserViewResponse> actual = userService.getAllUsers(0, 3, "name", "asc", filter, 2);
        List<UserViewResponse> expected = pagedUsers.stream()
                .map(UserViewResponse::Convert)
                .toList();

        assertEquals(actual, expected);
        assertNotNull(filter);

        verify(userRepository, times(1)).findUsersByUsername(anyString(), any(Pageable.class));
    }

    @DisplayName("The test when call with page size sortBy and sortOrder parameters that should return UserViewResponse list with pagination")
    @Test
    void getAllUsers_whenCallWithoutFilter_ShouldReturnUserViewResponseListWithPagination(){
        int page = 0;
        int size = 3;
        String sortBy = "name";
        String sortOrder = "asc";

        UserFixture userFixture = new UserFixture();
        List<User> userList = userFixture.createUserList();
        Page<User> pagedUsers = new PageImpl<>(userList);

        when(userRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy).ascending())))
                .thenReturn(pagedUsers);

        List<UserViewResponse> actual = userService.getAllUsers(page, size, sortBy, sortOrder, null, 2) ;
        List<UserViewResponse> expected = pagedUsers.stream()
                .map(UserViewResponse::Convert)
                .toList();

        assertEquals(actual, expected);

        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }

    @DisplayName("The test when call without filter that should sorts Users in descending order when sort order is descending")
    @Test
    void getAllUsers_whenCallWithoutFilter_ShouldSortsUsersDescending_whenSortOrderIsDescending(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "desc";

        UserFixture userFixture = new UserFixture();
        List<User> userList = userFixture.createUserList();
        Page<User> pagedUsers = new PageImpl<>(userList);
        when(userRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy).descending())))
                .thenReturn(pagedUsers);

        List<UserViewResponse> actual = userService.getAllUsers(page, size, sortBy, sortOrder, null, 2);
        List<UserViewResponse> expected = pagedUsers.stream()
                .map(UserViewResponse::Convert)
                .toList();

        assertEquals(actual, expected);

        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }
    @DisplayName("The test when call without filter and activeness is true should return active users in UserViewResponse type")
    @Test
    void getAllUsers_whenCallWithoutFilterAndActivenessIsTrue_ShouldReturnUserViewResponse(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "asc";
        Integer isActive = 1;

        UserFixture userFixture = new UserFixture();
        List<User> userList = userFixture.createUserList();
        Page<User> pagedUsers = new PageImpl<>(userList);

        when(userRepository.findByActivenessIs(eq(true), any(Pageable.class)))
                .thenReturn(pagedUsers);

        List<UserViewResponse> actual = userService.getAllUsers(page, size, sortBy, sortOrder, null, isActive);
        List<UserViewResponse> expected = pagedUsers.stream()
                .map(UserViewResponse::Convert)
                .toList();

        assertEquals(actual, expected);

        verify(userRepository, times(1)).findByActivenessIs(eq(true), any(Pageable.class));
    }

    @DisplayName("The test when call without filter and activeness is false should return inactive users in UserViewResponse type")
    @Test
    void getAllUsers_whenCallWithoutFilterAndActivenessIsFalse_ShouldReturnUserViewResponse(){
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String sortOrder = "asc";
        Integer isActive = 0;

        UserFixture userFixture = new UserFixture();
        List<User> userList = userFixture.createUserList();
        Page<User> pagedUsers = new PageImpl<>(userList);

        when(userRepository.findByActivenessIs(eq(false), any(Pageable.class)))
                .thenReturn(pagedUsers);

        List<UserViewResponse> actual = userService.getAllUsers(page, size, sortBy, sortOrder, null, isActive);
        List<UserViewResponse> expected = pagedUsers.stream()
                .map(UserViewResponse::Convert)
                .toList();

        assertEquals(actual, expected);

        verify(userRepository, times(1)).findByActivenessIs(eq(false), any(Pageable.class));
    }

    @DisplayName("The test when AddRemoveRoleRequest parameter is valid that should add role to the user and save the user to the db")
    @Test
    void addRole_whenAddRemoveRoleRequestParameterIsValid_ShouldAddRoleToTheUserAnd_ShouldSaveUserToTheDB(){
        AddRemoveRoleRequest addRemoveRoleRequest = new AddRemoveRoleRequest("test", 1L);
        UserFixture userFixture = new UserFixture();
        RoleFixture roleFixture = new RoleFixture();
        User user = userFixture.createUser();
        Role role = roleFixture.createRole();
        List<Role> userRoles = user.getRoles();


        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        userService.addRole(addRemoveRoleRequest);

        assertTrue(userRoles.contains(role));

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(roleRepository, times(1)).findById(anyLong());
        verify(userRepository).save(argThat(argument -> argument.getRoles().contains(role)));
    }

    @DisplayName("The test when user not found with username should throw Null Pointer Exception")
    @Test
    void addRole_whenUserNotFoundWithUsername_ShouldThrowNullPointerException(){
        AddRemoveRoleRequest addRemoveRoleRequest = new AddRemoveRoleRequest("test", 1L);
        String username = addRemoveRoleRequest.getUsername();

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> userService.addRole(addRemoveRoleRequest))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("User not found with username: %s ", addRemoveRoleRequest.getUsername()));
    }

    @DisplayName("The test when role not found with id should throw Null Pointer Exception")
    @Test
    void addRole_whenRoleNotFoundWithId_ShouldThrowNullPointerException(){
        AddRemoveRoleRequest addRemoveRoleRequest = new AddRemoveRoleRequest("test", 1L);
        Long id = addRemoveRoleRequest.getRoleId();
        UserFixture userFixture = new UserFixture();
        User user = userFixture.createUser();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(roleRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> userService.addRole(addRemoveRoleRequest))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Role not found with id: %s", addRemoveRoleRequest.getRoleId()));
    }
    @DisplayName("The test when User roles contains the role should throw Illegal State Exception")
    @Test
    void addRole_whenUserRolesContainsTheRole_ShouldThrowIllegalStateException(){
        AddRemoveRoleRequest addRemoveRoleRequest = new AddRemoveRoleRequest("test", 1L);
        UserFixture userFixture = new UserFixture();
        RoleFixture roleFixture = new RoleFixture();
        User user = userFixture.createUser();
        Role role = roleFixture.createRole();
        List<Role> roles = roleFixture.createRoleList();
        roles.add(role);
        user.setRoles(roles);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        assertThatThrownBy(()-> userService.addRole(addRemoveRoleRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("User already has the role with id: %s", role.getId()));
    }
    @DisplayName("The test when user saved to the database successfully should return a token in TokenResponseFormat ")
    @Test
    void saveUser_whenUserSavedToTheDBSuccessfully_ShouldReturnTokenAsTokenResponseFormat(){
        UserFixture userFixture = new UserFixture();
        RoleFixture roleFixture = new RoleFixture();
        UserRegisterRequest request = userFixture.createUserRegisterRequest();
        Role role = roleFixture.createRole();

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        TokenResponse response = userService.saveUser(request);

        assertNotNull(request.getUsername());
        assertNotNull(request.getPassword());
        assertNotNull(request.getEmail());
        verify(roleRepository, times(1)).findById(anyLong());
    }
    @DisplayName("The test when valid id and UserUpdateRequest parameters should update user and should return UpdatedUserResponse")
    @Test
    void updateUser_whenValidIdAndUserUpdateRequestParameters_ShouldReturnUpdatedUserResponse(){
        Long id = 1L;
        UserFixture userFixture = new UserFixture();
        UserUpdateRequest request = userFixture.createUserUpdateRequest();
        User user = userFixture.createUser();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UpdatedUserResponse response = userService.updateUser(id, request);

        assertNotNull(response.getUsername());
        assertNotNull(response.getEmail());
        assertNotNull(response.getName());

        verify(userRepository, times(1)).findById(id);
    }
    @DisplayName("The test that when user not found with given id should throw Null Pointer Exception")
    @Test
    void updateUser_whenUserNotFoundWithGivenId_ShouldReturnNullPointerException(){
        Long id = 1L;
        UserFixture userFixture = new UserFixture();
        UserUpdateRequest request = userFixture.createUserUpdateRequest();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> userService.updateUser(id, request))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("User not found with id(%s) that is provided:", id));

    }
    @DisplayName("The test that when user already exist with username or email should throw Illegal State Exception")
    @Test
    void updateUser_whenUserAlreadyExistWithUsernameOrEmail_ShouldThrowIllegalStateException(){
        Long id = 1L;
        UserFixture userFixture = new UserFixture();
        UserUpdateRequest request = userFixture.createUserUpdateRequest();
        User user = userFixture.createUser();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        assertThatThrownBy(()-> userService.updateUser(id, request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("username(%s) or email(%s) that you want to update is already in the database.",
                        request.getUsername(), request.getEmail()));

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).existsByUsername(request.getUsername());
    }
    @DisplayName("The test that when AddRemoveRoleRequest parameter is valid should remove role from user and the database")
    @Test
    void removeRole_whenAddRemoveRoleRequestParameterIsValid_ShouldRemoveRoleFromTheUserAndDB(){
        AddRemoveRoleRequest request = new AddRemoveRoleRequest("test", 1L);
        UserFixture userFixture = new UserFixture();
        RoleFixture roleFixture = new RoleFixture();
        User user = userFixture.createUser();
        List<Role> roles = roleFixture.createRoleList();
        user.setRoles(roles);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(roleRepository.findById(anyLong())).thenReturn(user.getRoles().stream().findAny());

        userService.removeRole(request);

        assertFalse(roles.contains(roleFixture.createRole()));

        verify(userRepository, times(1)).findByUsername(anyString());
        verify(roleRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(user);
    }

    @DisplayName("The test that when user already does not have the role should throw Illegal State Exception")
    @Test
    void removeRole_whenUserAlreadyDoesNotHaveTheRole_ShouldThrowIllegalStateException(){
        AddRemoveRoleRequest request = new AddRemoveRoleRequest("test", 1L);
        UserFixture userFixture = new UserFixture();
        RoleFixture roleFixture = new RoleFixture();
        User user = userFixture.createUser();
        Role role = roleFixture.createRole();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        assertThatThrownBy(()-> userService.removeRole(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("User already has not the role with id: %s", role.getId()));
    }
    @DisplayName("The test that when user already has just one role that can not be removed should throw Illegal State Exception")
    @Test
    void removeRole_whenUserAlreadyHasJustOneRole_ShouldThrowIllegalStateException(){
        AddRemoveRoleRequest request = new AddRemoveRoleRequest("test", 1L);
        UserFixture userFixture = new UserFixture();
        RoleFixture roleFixture = new RoleFixture();
        User user = userFixture.createUser();
        Role role = roleFixture.createRole();
        user.setRoles(Collections.singletonList(role));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        assertThatThrownBy(()-> userService.removeRole(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("User must have at least one role");
    }
    @DisplayName("The test that when user not found given username should throw Null Pointer Exception")
    @Test
    void removeRole_whenUserNotFoundWithUsername_ShouldThrowNullPointerException(){
        AddRemoveRoleRequest request = new AddRemoveRoleRequest("test", 1L);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());

        assertThatThrownBy(()-> userService.removeRole(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("User not found with username: %s ", request.getUsername()));
    }
    @DisplayName("The test that when role not found with given id should throw Null Pointer Exception")
    @Test
    void removeRole_whenRoleNotFoundWithGivenId_ShouldThrowNullPointerException(){
        AddRemoveRoleRequest request = new AddRemoveRoleRequest("test", 1L);
        UserFixture userFixture = new UserFixture();
        User user = userFixture.createUser();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(()-> userService.removeRole(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("Role not found with id: %s", request.getRoleId()));
    }
    @DisplayName("The test that when valid id parameter should make false the activeness of the user")
    @Test
    void softDeleteUser_whenValidIdParameter_ShouldMakeFalseTheActivenessOfTheUser(){
        UserFixture userFixture = new UserFixture();
        User user = userFixture.createUser();
        Long id = user.getId();
        user.setActiveness(true);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.softDeleteUser(id);

        assertFalse(user.isActiveness());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);

    }
    @DisplayName("The test that when user not found with given id should throw Null Pointer Exception")
    @Test
    void softDeleteUser_whenUserNotFoundWithGivenId_ShouldThrowNullPointerException(){
        UserFixture userFixture = new UserFixture();
        User user = userFixture.createUser();
        Long id = user.getId();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> userService.softDeleteUser(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("User not found with id(%s) that is provided:", id));
    }
    @DisplayName("The test that when user already inactive should throw Null Pointer Exception")
    @Test
    void softDeleteUser_whenUserAlreadyInactive_ShouldThrowIllegalStateException(){
        UserFixture userFixture = new UserFixture();
        User user = userFixture.createUser();
        Long id = user.getId();
        user.setActiveness(false);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        assertThatThrownBy(()-> userService.softDeleteUser(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("User %s is already inactive.", user.getUsername()));
    }
    @DisplayName("The test that when valid id parameter should make true the activeness of the user")
    @Test
    void activateUser_whenValidIdParameter_ShouldMakeTrueTheActivenessOfTheUser(){
        UserFixture userFixture = new UserFixture();
        User user = userFixture.createUser();
        Long id = user.getId();
        user.setActiveness(false);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.ActivateUser(id);

        assertTrue(user.isActiveness());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);

    }

    @DisplayName("The test that when user not found with given id should throw Null Pointer Exception")
    @Test
    void activateUser_whenUserNotFoundWithGivenId_ShouldThrowNullPointerException(){
        UserFixture userFixture = new UserFixture();
        User user = userFixture.createUser();
        Long id = user.getId();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> userService.ActivateUser(id))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining(String.format("User not found with id(%s) that is provided:", id));
    }
    @DisplayName("The test that when user already active should throw Null Pointer Exception")
    @Test
    void activateUser_whenUserAlreadyActive_ShouldThrowIllegalStateException(){
        UserFixture userFixture = new UserFixture();
        User user = userFixture.createUser();
        Long id = user.getId();
        user.setActiveness(true);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        assertThatThrownBy(()-> userService.ActivateUser(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("User %s is already active.", user.getUsername()));
    }
}
