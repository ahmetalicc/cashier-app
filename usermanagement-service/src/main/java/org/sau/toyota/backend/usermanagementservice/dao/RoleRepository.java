package org.sau.toyota.backend.usermanagementservice.dao;

import org.sau.toyota.backend.usermanagementservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
