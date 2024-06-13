package org.sau.toyota.backend.usermanagementservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.usermanagementservice.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatedUserResponse {

    private String name;

    private String username;

    private String email;

    public static UpdatedUserResponse Convert(User user){
        return UpdatedUserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
