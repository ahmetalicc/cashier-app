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

import java.util.List;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
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
        byte[] bytes = new byte[0];
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
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    log.debug("HTTP request does not contain valid header.");
                    return generateErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Token is not valid");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Authorization", authHeader);
                HttpEntity<Object> httpRequest = new HttpEntity<>(httpHeaders);
                // Communicating with authentication-service to check whether the token is valid or not.
                try {
                    restTemplate.exchange(authServiceUrl, HttpMethod.GET, httpRequest, Object.class);
                } catch (HttpClientErrorException e) {
                    log.warn("According to the authentication-service response, token couldn't be validated.");
                    return generateErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Token is not valid!");
                }

                var token = authHeader.substring(7);
                List<String> roles = jwtUtil.parseTokenGetRoles(token);
                String username = jwtUtil.getUsernameFromToken(token);

                // It is not mandatory to have any specific role to access these endpoints.
                // Anyone who has any role can access the ~/product/** endpoints.
                if (exchange.getRequest().getURI().getPath().startsWith("/product/")){
                    if (roles.isEmpty()){
                        log.warn(String.format("user: %s does not have role. ", username));
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                    }
                }
                // It is not mandatory to have any specific role to access these endpoints.
                // Anyone who has any role can access the ~/category/** endpoints.
                else if (exchange.getRequest().getURI().getPath().startsWith("/category/")){
                    if (roles.isEmpty()){
                        log.warn(String.format("user: %s does not have role. ", username));
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                    }
                }
                // It is not mandatory to have any specific role to access these endpoints.
                // Anyone who has any role can access the ~/campaign/** endpoints.
                else if (exchange.getRequest().getURI().getPath().startsWith("/campaign/")){
                    if (roles.isEmpty()){
                        log.warn(String.format("user: %s does not have role. ", username));
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                    }
                }
                // Store_Manager role is necessary to be able to access ~/report/** endpoints.
                else if (exchange.getRequest().getURI().getPath().startsWith("/report/")) {
                    if(!roles.contains("ROLE_STORE_MANAGER")) {
                        log.warn(String.format("User %s does not have store_manager role to access /report/** endpoint.", username));
                        return generateErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "User does not have store_manager role to access /report/** endpoint.");
                    }
                }
                // Cashier role is necessary to be able to access ~/sale/** endpoints.
                else if (exchange.getRequest().getURI().getPath().startsWith("/sale/")) {
                    if(!roles.contains("ROLE_CASHIER")) {
                        log.warn(String.format("User %s does not have cashier role to access /sale/** endpoint.", username));
                        return generateErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "User does not have cashier role to access /sale/** endpoint.");
                    }
                }
                // Admin role is necessary to be able to access ~/report/** endpoints.
                else if (exchange.getRequest().getURI().getPath().startsWith("/user/")) {
                    if (!roles.contains("ROLE_ADMIN")) {
                        log.warn(String.format("User %s does not have admin role to access /user/** endpoint.", username));
                        return generateErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "User does not have admin role to access /user/** endpoint.");
                    }
                } else {
                    log.debug("The attempt to access the endpoint was unsuccessful.");
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
