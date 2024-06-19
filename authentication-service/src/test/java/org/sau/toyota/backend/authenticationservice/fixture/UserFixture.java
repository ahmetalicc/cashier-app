package org.sau.toyota.backend.authenticationservice.fixture;
import org.sau.toyota.backend.authenticationservice.entity.User;

import java.util.ArrayList;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Fixture class for generating User objects with fake data.
 */
public class UserFixture extends Fixture<User>{

    /**
     * Creates a new User object with randomly generated fake data.
     *
     * @return A User object with fake data.
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

}
