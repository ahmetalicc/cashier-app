package org.sau.toyota.backend.authenticationservice.dao;

import org.sau.toyota.backend.authenticationservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Repository interface that extends JpaRepository for {@link User} entity.
 * Provides methods to perform CRUD operations and custom queries on User data.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by username in database.
     *
     * @param username the username of the user
     * @return an {@link Optional} containing the user if found, or empty if not
     */
    Optional<User> findByUsername(String username);
    /**
     * Checks if a user with the given username exists in database.
     *
     * @param username the username to check
     * @return true if a user with the given username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user with the given email exists in database.
     *
     * @param email the email to check
     * @return true if a user with the given email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Finds users whose usernames contain the specified filter string.
     *
     * @param filter the filter string to search for in usernames
     * @param pageable the pagination information
     * @return a {@link Page} of users whose usernames contain the filter string
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<User> findUsersByUsername(@Param("filter") String filter, Pageable pageable);

    /**
     * Finds users by their activeness status.
     *
     * @param isActive the activeness status of the users to find
     * @param pageable the pagination information
     * @return a {@link Page} of users with the specified activeness status
     */
    Page<User> findByActivenessIs(boolean isActive, Pageable pageable);


}
