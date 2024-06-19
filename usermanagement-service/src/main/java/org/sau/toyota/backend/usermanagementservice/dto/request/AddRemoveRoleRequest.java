package org.sau.toyota.backend.usermanagementservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * DTO (Data Transfer Object) representing the request for adding or removing a role from a user.
 * Contains the username of the user and the ID of the role to be added or removed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRemoveRoleRequest {

    /**
     * The username of the user for which the role is to be added or removed.
     */
    private String username;

    /**
     * The ID of the role to be added or removed.
     */
    private Long roleId;
}