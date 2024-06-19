package org.sau.toyota.backend.usermanagementservice.config;

import lombok.RequiredArgsConstructor;
import org.sau.toyota.backend.usermanagementservice.security.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * JwtAuthenticationFilter class extends OncePerRequestFilter to handle JWT authentication for incoming requests.
 * It extracts the JWT token from the Authorization header, validates it, and sets up the authentication context
 * if the token is valid. It uses JwtService to perform token operations and UserDetailsService to load user details.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;
    /**
     * Filters the incoming HTTP request to extract and validate the JWT token.
     * If the token is valid and the user is authenticated, sets up the authentication context.
     *
     * @param request     The HttpServletRequest object representing the incoming request.
     * @param response    The HttpServletResponse object representing the outgoing response.
     * @param filterChain The FilterChain object to proceed with the next filters in the chain.
     * @throws ServletException If an error occurs during the filter processing.
     * @throws IOException      If an I/O error occurs during the filter processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization"); // Get the Authorization header
        final String jwt;
        final String username;

        // Check if Authorization header is missing or does not start with "Bearer"
        if(header == null || !header.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        // Extract JWT token from the Authorization header
        jwt = header.substring(7);
        username = jwtService.findUsername(jwt);

        // Check if username is not null and there is no existing authentication context
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate JWT token and create authentication token if valid
            if(jwtService.tokenControl(jwt, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
