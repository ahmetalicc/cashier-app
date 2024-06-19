package org.sau.toyota.backend.usermanagementservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Configuration class for defining security configurations.
 * This class configures security filters and rules for HTTP requests.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the security filter chain and HTTP request authorization rules.
     * This method defines the security rules for different endpoints and sets up
     * the authentication provider and JWT authentication filter.
     *
     * @param http The HttpSecurity object to configure security.
     * @return A SecurityFilterChain object representing the configured security filter chain.
     * @throws Exception If an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/login/**")
                .permitAll()
                .antMatchers("/product/**")
                .permitAll()
                .antMatchers("/user/**")
                .hasRole("ADMIN")
                .antMatchers("/category/**")
                .permitAll()
                .antMatchers("/campaign/**")
                .permitAll()
                .antMatchers("/sale/**")
                .hasRole("CASHIER")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
