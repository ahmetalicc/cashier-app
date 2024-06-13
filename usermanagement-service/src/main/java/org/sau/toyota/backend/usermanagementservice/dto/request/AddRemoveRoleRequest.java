package org.sau.toyota.backend.usermanagementservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRemoveRoleRequest {

    private String username;

    private Long roleId;
}
