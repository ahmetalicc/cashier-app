package org.sau.toyota.backend.usermanagementservice.config;

import lombok.RequiredArgsConstructor;
import org.sau.toyota.backend.usermanagementservice.dao.UserRepository;
import org.sau.toyota.backend.usermanagementservice.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    /**
     * Loads user details by username.
     *
     * @param username the username identifying the user
     * @return a fully populated user record
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new NullPointerException("User not found with username:"+ username));
        return new CustomUserDetails(user);
    }
}
