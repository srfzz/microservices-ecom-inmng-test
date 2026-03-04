package com.micro_ecommerce.api_gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    @Override

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("Global filter executed for requestt: {}", exchange.getRequest().getURI());
        }));
        // throw new UnsupportedOperationException("Unimplemented method 'filter'");
    }

    @Override
    public int getOrder() {
        return 5; // Ensure this filter runs before the default filters
    }

}
