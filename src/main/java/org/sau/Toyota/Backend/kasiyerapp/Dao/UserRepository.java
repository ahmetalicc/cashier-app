package org.sau.Toyota.Backend.kasiyerapp.Dao;

import org.sau.Toyota.Backend.kasiyerapp.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<User> findUsersByUsername(@Param("filter") String filter, Pageable pageable);

    Page<User> findByActivenessIs(boolean isActive, Pageable pageable);


}
