package org.sau.toyota.backend.authenticationservice.fixture;
import org.sau.toyota.backend.authenticationservice.entity.User;

import java.util.ArrayList;

public class UserFixture extends Fixture<User>{

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

}
