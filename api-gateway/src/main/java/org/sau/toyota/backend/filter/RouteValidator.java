package org.sau.toyota.backend.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/** @author Ahmet Alıç
 * @since 14-06-2024
 *
 * Validates routes to determine if they are secured or open based on a predefined list of open API endpoints.
 * This class is typically used as a component to check if a route is secured or open for public access.
 */
@Component
public class RouteValidator {

    /**
     * Predefined list of open API endpoints that do not require authentication.
     */
    public static final List<String> openApiEndpoints = List.of(
            "/auth/login",
            "/eureka/**",
            "/auth/validate"
    );

    /**
     * Predicate to determine if a request is secured based on the predefined open API endpoints.
     */
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
