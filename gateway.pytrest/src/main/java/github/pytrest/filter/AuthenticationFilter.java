package github.pytrest.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator validator;
    @Autowired
    private JwtUtil jwtUtil;

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

    private void authenticate(ServerWebExchange exchange) {
        if (validator.isSecured.test(exchange.getRequest())) {
            String token = getJwtToken(exchange);
            validate(token);
        }
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

    private void validate(String jwtToken) {
        try {
            jwtUtil.validateJwtToken(jwtToken);
        }
        catch (Exception e) {
            System.out.println("Invalid access");
            throw new RuntimeException("Unauthorized access to application");
        }
    }
    
    public static class Config {
    }
}
