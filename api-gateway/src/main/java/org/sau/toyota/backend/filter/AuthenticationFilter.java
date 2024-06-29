package org.sau.toyota.backend.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.sau.toyota.backend.core.results.ErrorResult;
import org.sau.toyota.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** @author Ahmet Alıç
 * @since 14-06-2024
 * This class represents an authentication filter for handling JWT token authentication and role-based access control.
 * It is a custom Gateway Filter Factory class for Spring Cloud Gateway.
 * This filter intercepts incoming requests, validates JWT tokens, and checks if the user has the necessary roles
 * to access specific endpoints.
 *
 * Require the instances of RouteValidator, JwtUtil, RestTemplate
 */
@Component
@Log4j2
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${auth-service.url}")
    private String authServiceUrl;
    public AuthenticationFilter() {
        super(Config.class);
    }

    /**
     * Generates an error response in JSON format with the given HTTP status code and message.
     *
     * @param exchange The ServerWebExchange object representing the HTTP request and response
     * @param httpStatus The HTTP status code for the error response
     * @param message The error message to be included in the response body
     * @return A Mono<Void> representing the completion of writing the error response to the HTTP response
     */
    private Mono<Void> generateErrorResponse(ServerWebExchange exchange, HttpStatus httpStatus, String message){
        ErrorResult errorResult = new ErrorResult(message);
        ObjectMapper objectMapper = new ObjectMapper();
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(errorResult);
        } catch (JsonProcessingException e) {
            return Mono.empty();
        }
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    /**
     * Applies a GatewayFilter to validate and authorize requests based on JWT authentication.
     * This method is responsible for processing incoming requests, validating JWT tokens,
     * and checking if the user has the necessary roles to access specific endpoints.
     * If the token is invalid or the user lacks the required roles, it returns an HTTP 401 Unauthorized response.
     * If the requested endpoint is not found, it returns an HTTP 404 Not Found response.
     *
     * @param config The configuration for the GatewayFilter
     * @return The GatewayFilter that validates and authorizes requests
     *
     * @throws ResponseStatusException when the HTTP request does not contain an authorization header
     * @throws ResponseStatusException when the token couldn't be validated by the authentication service
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.debug("HTTP request does not contain valid header.");
                    return generateErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Token is not valid");
                }
                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Authorization", authHeader);
                HttpEntity<Object> httpRequest = new HttpEntity<>(httpHeaders);

                if (!validateTokenWithAuthService(httpRequest)) {
                    return generateErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Token is not valid!");
                }

                var token = authHeader.substring(7);
                List<String> roles = jwtUtil.parseTokenGetRoles(token);
                String username = jwtUtil.getUsernameFromToken(token);

                if (!hasRequiredRole(exchange.getRequest().getURI().getPath(), roles, username)) {
                    return generateErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "User does not have the required role to access this endpoint.");
                }
            }
            return chain.filter(exchange);
        };
    }
    /**
     * Validates the token with the authentication service by sending a request with the given HTTP entity.
     *
     * @param httpRequest The HTTP entity containing the authorization header
     * @return True if the token is valid, otherwise false
     */
    private boolean validateTokenWithAuthService(HttpEntity<Object> httpRequest) {
        try {
            restTemplate.exchange(authServiceUrl, HttpMethod.GET, httpRequest, Object.class);
            return true;
        } catch (HttpClientErrorException e) {
            log.warn("According to the authentication-service response, token couldn't be validated.");
            return false;
        }
    }
    /**
     * Map defining required roles for accessing specific endpoint paths.
     * <p>
     * Key: Endpoint path prefix.
     * Value: Required role (empty string if no specific role is required).
     * </p>
     */
    private static final Map<String, String> requiredRoles = new HashMap<>();

    static {
        requiredRoles.put("/product/", "");
        requiredRoles.put("/category/", "");
        requiredRoles.put("/campaign/", "");
        requiredRoles.put("/report/", "ROLE_STORE_MANAGER");
        requiredRoles.put("/sale/", "ROLE_CASHIER");
        requiredRoles.put("/user/", "ROLE_ADMIN");
    }
    /**
     * Checks if the user has the required role to access a given endpoint.
     * <p>
     * This method verifies whether the user has the necessary role to access a specific path.
     * It uses a predefined map of required roles for different paths and matches the user's roles
     * against the required role for the path.
     * </p>
     *
     * @param path     The path of the endpoint being accessed.
     * @param roles    A list of roles assigned to the user.
     * @param username The username of the user attempting to access the endpoint.
     * @return {@code true} if the user has the required role or if the endpoint does not require a specific role and the user has at least one role; {@code false} otherwise.
     * @throws ResponseStatusException if the endpoint is not found in the predefined roles map.
     */
    public boolean hasRequiredRole(String path, List<String> roles, String username) {
        String requiredRole = requiredRoles.entrySet().stream()
                .filter(entry -> path.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endpoint not found"));

        if (requiredRole.isEmpty()) {
            if (roles.isEmpty()) {
                log.error(String.format("User %s does not have any role.", username));
                return false;
            }
        } else if (!roles.contains(requiredRole)) {
            log.warn(String.format("User %s does not have %s role to access endpoint.", username, requiredRole.toLowerCase()));
            return false;
        }

        return true;
    }

    /**
     * Configuration class for the AuthenticationFilter.
     * This class can be used to define custom configuration properties if needed.
     */
    public static class Config {
        // Configuration properties if needed
    }
}
