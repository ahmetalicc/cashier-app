package org.sau.Toyota.Backend.kasiyerapp.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    private String username;

    private String password;


}
