package github.pytrest.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public Predicate<ServerHttpRequest> isSecured = request -> {
        PathMatcher pathMatcher = new AntPathMatcher();
        return openApiEndpoints.stream()
            .noneMatch(uri -> pathMatcher.match(uri, request.getURI().getPath()));
    };

    public static final List<String> openApiEndpoints = List.of(
        "/auth/register",
        "/auth/login",
        "/auth/validate",
        "/auth/{username}/profile",

        "/collections/{collectionId}/collection",
        "/collections/{username}/collections",

        "/images/{imageId}/image",

        "/eureka",
        "/error"
    );
}