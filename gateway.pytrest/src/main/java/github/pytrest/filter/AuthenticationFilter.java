package github.pytrest.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator validator;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            authenticate(exchange);
            return chain.filter(exchange);
        });
    }

    private Mono<Void> authenticate(ServerWebExchange exchange) {
        if (validator.isSecured.test(exchange.getRequest())) {
            String token = getJwtToken(exchange);
            return validate(token, exchange);
        }
        return Mono.empty();
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

    private Mono<Void> validate(String jwtToken, ServerWebExchange exchange) {
        return webClientBuilder.build()
            .post()
            .uri("http://IDENTITY-SERVICE/auth/validate")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
            .retrieve()
            .bodyToMono(Boolean.class)
            .flatMap(isValid -> checkAuthStatus(isValid, exchange));
    }

    private Mono<Void> checkAuthStatus(Boolean isTokenValid, ServerWebExchange exchange) {
        if (Boolean.TRUE.equals(isTokenValid)) {
            return Mono.empty();
        }
        else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    public static class Config {
    }
}
