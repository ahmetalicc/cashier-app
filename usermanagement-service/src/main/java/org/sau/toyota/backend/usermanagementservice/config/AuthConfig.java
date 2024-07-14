package org.sau.toyota.backend.usermanagementservice.config;

import lombok.RequiredArgsConstructor;
import org.sau.toyota.backend.usermanagementservice.dao.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * AuthConfig is a configuration class for setting up Spring Security.
 * It defines beans and configurations for user authentication and authorization.
 */
@Configuration
@RequiredArgsConstructor
public class AuthConfig {

    private final UserRepository userRepository;
    /**
     * Creates a UserDetailsService bean that uses the custom UserRepository.
     *
     * @return a UserDetailsService implementation
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsService(userRepository);
    }
    /**
     * Creates an AuthenticationProvider bean using a DaoAuthenticationProvider.
     * Sets the custom UserDetailsService and password encoder.
     *
     * @return an AuthenticationProvider implementation
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    /**
     * Creates a PasswordEncoder bean using BCryptPasswordEncoder.
     *
     * @return a PasswordEncoder implementation
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * Creates an AuthenticationManager bean from the provided AuthenticationConfiguration.
     *
     * @param authenticationConfiguration the AuthenticationConfiguration to use
     * @return the configured AuthenticationManager
     * @throws Exception if an error occurs while retrieving the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
