package com.micro_ecommerce.api_gateway.filters;

import com.micro_ecommerce.api_gateway.services.jwtService;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {
    private final jwtService jwtService;

    public AuthenticationGatewayFilterFactory(jwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Data
    public static class Config {
        private Boolean enabled;
    }

    @Override
    public GatewayFilter apply(Config config) {
        // TOD O Auto-generated method stub
        return (exchnage, chain) -> {
            if (config.getEnabled()) {
                log.info("Authentication filter is enabled. Processing request: {}", exchnage.getRequest().getURI());
                String authHeader = exchnage.getRequest().getHeaders().getFirst("Authorization");
                if (authHeader == null || authHeader.isEmpty() || !authHeader.contains("Bearer")) {
                    log.warn("Missing Authorization header for request: {}", exchnage.getRequest().getURI());
                    // You can choose to return an error response here if authentication fails
                    exchnage.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchnage.getResponse().setComplete();
                }
                String token = authHeader.replace("Bearer ", "");
                String userRole = jwtService.getUserRoleFromToken(token);
                log.info("Extracted user role: {} from token for request: {}", userRole,

                        exchnage.getRequest().getURI());
                Long userId = jwtService.getUserIdFromToken(token);
                ServerWebExchange mutatedExchange = exchnage.mutate()
                        .request(exchnage.getRequest().mutate()
                                .header("X-User-Id", String.valueOf(userId))
                                .header("X-User-Role", userRole)
                                .build())
                        .build();

                
                log.info("Headers: {}", authHeader);
                log.info("Injected headers for user {} at station {}", userId, exchnage.getRequest().getURI());
                return chain.filter(mutatedExchange);

                // Here you can add your authentication logic, e.g., check headers, validate
                // tokens, etc.
            } else {
                log.info("Authentication filter is disabled. Skipping authentication for request: {}",
                        exchnage.getRequest().getURI());
            }
            return chain.filter(exchnage);
        };
    }

}
