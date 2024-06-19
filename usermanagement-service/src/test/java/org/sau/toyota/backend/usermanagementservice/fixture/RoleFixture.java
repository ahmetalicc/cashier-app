package org.sau.toyota.backend.usermanagementservice.fixture;


import org.sau.toyota.backend.usermanagementservice.entity.Role;

import java.util.ArrayList;
import java.util.List;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Fixture class for generating Role objects with fake data.
 */
public class RoleFixture extends Fixture<Role>{

    UserFixture userFixture = new UserFixture();
    /**
     * Creates a list of Role objects with randomly generated fake data.
     *
     * @return List of Role objects
     */
    public List<Role> createRoleList() {

        List<Role> roles = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Role role = Role.builder()
                    .id(faker.number().randomNumber())
                    .name(faker.lorem().word())
                    .users(new ArrayList<>())
                    .build();
            roles.add(role);
        }

        return roles;
    }
    /**
     * Creates a single Role object with randomly generated fake data.
     *
     * @return Role object
     */
    public Role createRole(){
        Role role = new Role();
        Role.builder()
                .id(faker.number().randomNumber())
                .name(faker.gameOfThrones().character())
                .users(userFixture.createUserList())
                .build();
        return role;
    }
}
