package org.sau.toyota.backend.usermanagementservice.dao;

import org.sau.toyota.backend.usermanagementservice.entity.Role;
import org.sau.toyota.backend.usermanagementservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Repository interface that extends JpaRepository for {@link Role} entity.
 * Provides methods to perform CRUD operations and custom queries on Role data.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
