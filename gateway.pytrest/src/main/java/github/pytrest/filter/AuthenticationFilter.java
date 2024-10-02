package github.pytrest.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final RouteValidator validator;
    private final RestTemplate template;

    public AuthenticationFilter(RouteValidator routeValidator, RestTemplate template) {
        super(Config.class);
        this.validator = routeValidator;
        this.template = template;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            exchange = authenticate(exchange);
            return chain.filter(exchange);
        });
    }

    private ServerWebExchange authenticate(ServerWebExchange exchange) {
        if (validator.isSecured.test(exchange.getRequest())) {
            String token = getJwtToken(exchange);
            return validate(token, exchange);
        }
        return exchange;
    }

    private String getJwtToken(ServerWebExchange exchange) {
        String authHeader = getAuthHeader(exchange);
        return extractJwtToken(authHeader);
    }

    private String getAuthHeader(ServerWebExchange exchange) {
        if (exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        }
        throw new RuntimeException("missing authorization header");
    }

    private String extractJwtToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new RuntimeException("Incorrect authorization method. Use Bearer");
    }

    private ServerWebExchange validate(String jwtToken, ServerWebExchange exchange) {
        HttpEntity<String> entity = new HttpEntity<>(jwtToken);
        
        ResponseEntity<String> response = template.exchange(
            "http://192.168.0.106:8180/auth/validate",
            HttpMethod.POST,
            entity,
            String.class
        );
        return checkAuthStatus(response.getBody(), exchange);
    }

    private ServerWebExchange checkAuthStatus(String username, ServerWebExchange exchange) {
        if (!username.isEmpty()) {
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
            .header("X-Authenticated-Username", username)
            .build();
        return exchange.mutate().request(modifiedRequest).build();
        }
        else {
            throw new RuntimeException("Unauthorized access: username is null or empty");
        }
    }

    public static class Config {
    }
}
