package org.sau.toyota.backend.usermanagementservice.fixture;


import org.sau.toyota.backend.usermanagementservice.dto.request.UserRegisterRequest;
import org.sau.toyota.backend.usermanagementservice.dto.request.UserUpdateRequest;
import org.sau.toyota.backend.usermanagementservice.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Fixture class for generating User objects with fake data.
 */
public class UserFixture extends Fixture<User>{

    /**
     * Creates a list of User objects with randomly generated fake data.
     *
     * @return List of User objects
     */
    public List<User> createUserList() {
        List<User> userList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            userList.add(User.builder()
                    .id(faker.number().randomNumber())
                    .email(faker.internet().emailAddress())
                    .name(faker.name().fullName())
                    .username(faker.name().username())
                    .activeness(faker.bool().bool())
                    .password(faker.internet().password())
                    .roles(new ArrayList<>())
                    .build());
        }

        return userList;
    }
    /**
     * Creates a single User object with randomly generated fake data.
     *
     * @return User object
     */
    public User createUser() {
        User user = new User();

        user.setId(faker.number().randomNumber());
        user.setEmail(faker.internet().emailAddress());
        user.setUsername(faker.name().username());
        user.setName(faker.name().fullName());
        user.setActiveness(faker.bool().bool());
        user.setPassword(faker.internet().password());
        user.setRoles(new ArrayList<>());

        return user;
    }
    /**
     * Creates a mock UserRegisterRequest object with randomly generated fake data.
     *
     * @return A UserRegisterRequest object with random data.
     */
    public UserRegisterRequest createUserRegisterRequest(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();

        userRegisterRequest.setName(faker.name().fullName());
        userRegisterRequest.setUsername(faker.name().username());
        userRegisterRequest.setPassword(faker.internet().password());
        userRegisterRequest.setEmail(faker.internet().emailAddress());
        userRegisterRequest.setRoleId(Collections.singletonList(faker.number().randomNumber()));

        return userRegisterRequest;
    }
    /**
     * Creates a mock UserUpdateRequest object with randomly generated fake data.
     *
     * @return A UserUpdateRequest object with random data.
     */
    public UserUpdateRequest createUserUpdateRequest(){
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();

        userUpdateRequest.setName(faker.name().fullName());
        userUpdateRequest.setUsername(faker.gameOfThrones().character());
        userUpdateRequest.setPassword(faker.internet().password());
        userUpdateRequest.setEmail(faker.internet().emailAddress());

        return userUpdateRequest;
    }
}
