package com.example.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class GatewayService implements GlobalFilter{

    private static final Logger logger = LoggerFactory.getLogger(GatewayService.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Log request information specific to this route
        String uuidRequest = UUID.randomUUID().toString();

        logger.info("---------------------------------------------------------------------------");
        logger.info("Api Gateway Logging Filter - Request Path: {}", exchange.getRequest().getPath());
        logger.info("Api Gateway Logging Filter - Request Method: {}", exchange.getRequest().getMethod());
        logger.info("Api Gateway Logging Filter - Request Hashcode: {}", uuidRequest);
        logger.info("Api Gateway Logging Filter - Response Code: {}", exchange.getResponse().getStatusCode());
        logger.info("Api Gateway Logging Filter - Response Output: {}", exchange.getResponse().getCookies().toString());
        logger.info("---------------------------------------------------------------------------");
        // You can add more specific logging based on the route

        exchange =exchange.mutate().request(builder -> builder.header("TRACE-CODE", uuidRequest)).build();

        // Process the request
        return chain.filter(exchange);
    }


//    @Override
//    public int getOrder() {
//        return -1;
//    }
}
