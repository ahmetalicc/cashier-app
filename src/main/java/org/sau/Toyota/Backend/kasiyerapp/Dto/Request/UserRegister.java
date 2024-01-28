package org.sau.Toyota.Backend.kasiyerapp.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister {

    private String name;

    private String username;

    private String password;

    private String email;

    private List<Long> RoleId;
}
