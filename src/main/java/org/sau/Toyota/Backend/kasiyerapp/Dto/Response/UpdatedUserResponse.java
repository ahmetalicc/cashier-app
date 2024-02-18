package org.sau.Toyota.Backend.kasiyerapp.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.Toyota.Backend.kasiyerapp.Entity.User;

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
