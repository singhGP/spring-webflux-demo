package com.seasia.webflux.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;


import com.seasia.webflux.handler.RequestHandler;

@Configuration
public class RouterFuctionConfig {

	@Bean
	public RouterFunction<ServerResponse> route(RequestHandler handler){
		return RouterFunctions
				.route(GET("/api/flux").and(accept(MediaType.APPLICATION_JSON)),handler::flux )
				.andRoute(GET("/api/mono").and(accept(MediaType.APPLICATION_JSON)),handler::mono);
	}
	
}
