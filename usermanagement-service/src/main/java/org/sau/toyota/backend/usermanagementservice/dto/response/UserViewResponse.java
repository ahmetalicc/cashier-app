package org.sau.toyota.backend.usermanagementservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.usermanagementservice.entity.Role;
import org.sau.toyota.backend.usermanagementservice.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserViewResponse {

    private String name;

    private String username;

    private String email;

    private boolean is_active;

    private List<String> roles;

    public static UserViewResponse Convert(User user){
        return UserViewResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .is_active(user.isActiveness())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }


}
