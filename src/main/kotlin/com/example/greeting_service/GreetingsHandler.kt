package com.example.greeting_service

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.security.Principal

@Component
class GreetingsHandler(val service: GreetingService){

    fun handleGreetings(request : ServerRequest): Mono<ServerResponse> {


        val greetingResponseFlux = request
                .principal()
                .map(Principal::getName)
                .map {GreetingRequest(it)}
                .flatMapMany { service.greetMany(it) }

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(greetingResponseFlux, GreetingResponse::class.java)

    }

    fun handleGreeting(request : ServerRequest): Mono<ServerResponse> {

        val greetingResponseMono = request
                .principal()
                .map(Principal::getName )
                .map {GreetingRequest(it)}
                .flatMap { service.greetOnce(it) }

        return ServerResponse.ok()
                .body(greetingResponseMono, GreetingResponse::class.java)

    }

}