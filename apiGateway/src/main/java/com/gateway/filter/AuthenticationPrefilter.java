//package com.gateway.filter;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.gateway.model.Authorities;
//import com.gateway.model.ValidationResponse;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.WebClientResponseException;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//@Slf4j
//public class AuthenticationPrefilter extends AbstractGatewayFilterFactory<AuthenticationPrefilter.Config> {
//
//    private final WebClient.Builder webClientBuilder;
//
//    public AuthenticationPrefilter(WebClient.Builder webClientBuilder) {
//        super(Config.class);
//        this.webClientBuilder = webClientBuilder;
//    }
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            log.info("URL: " + request.getURI().getPath());
//            String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//            log.info("Bearer Token: " + bearerToken);
//
//            if( !( request.getURI().getPath().contains("/api/auth/login") || request.getURI().getPath().contains("/api/auth/token/refresh") ) ) {
//
//                return webClientBuilder.build().get()
//                        .uri("lb://authService/api/auth/token/validate")
//                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
//                        .retrieve().bodyToMono(ValidationResponse.class)
//                        .map(response -> {
//                            exchange.getRequest().mutate().header("username", response.getUsername());
//
//                            exchange.getRequest().mutate().header("roles",
//                                    response.getAuthorities().stream().map(Authorities::getAuthority).reduce("", (a, b) -> a + "," + b));
//                            return exchange;
//                        }).flatMap(chain::filter).onErrorResume(error -> {
//                            log.info("Error Happened",error);
//                            HttpStatus errorCode = null;
//                            String errorMsg = "";
//                            if (error instanceof WebClientResponseException) {
//                                WebClientResponseException webCLientException = (WebClientResponseException) error;
//                                errorCode = webCLientException.getStatusCode();
//                                errorMsg = webCLientException.getStatusText();
//
//                            } else {
//                                errorCode = HttpStatus.BAD_GATEWAY;
//                                errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
//                            }
////                            AuthorizationFilter.AUTH_FAILED_CODE
//                            return onError(exchange, String.valueOf(errorCode.value()) ,errorMsg, "JWT Authentication Failed", errorCode);
//
//
//                        });
//            }
//
//            return chain.filter(exchange);
//        };
//    }
//
//    private Mono<Void> onError(ServerWebExchange exchange, String errCode, String err, String errDetails, HttpStatus httpStatus) {
//        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(httpStatus);
//        try {
//            response.getHeaders().add("Content-Type", "application/json");
//            byte[] byteData = objectMapper.writeValueAsBytes(errDetails);
//            return response.writeWith(Mono.just(byteData).map(t -> dataBufferFactory.wrap(t)));
//
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//
//        }
//        return response.setComplete();
//    }
//
//    @NoArgsConstructor
//    public static class Config {
//
//
//    }
//}