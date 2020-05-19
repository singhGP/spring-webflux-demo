package com.seasia.webflux.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WebfluxController {

	@GetMapping("/flux")
	public Flux<Integer> list() {
		return Flux.just(1, 2, 3, 4, 5).delayElements(Duration.ofSeconds(1)).log();

	}

	@GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Integer> StringStream() {
		return Flux.just(1, 2, 3, 4, 5).delayElements(Duration.ofSeconds(1)).log();

	}

	@GetMapping(value = "/infinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Long> returnInfinteStream() {
		return Flux.interval(Duration.ofSeconds(1)).log();

	}

	@GetMapping(value = "/mono")
	public Mono<Integer> returnMono() {
		return Mono.just(41).log();

	}
}
