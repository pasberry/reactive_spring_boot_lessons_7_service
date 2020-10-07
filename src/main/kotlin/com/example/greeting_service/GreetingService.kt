package com.example.greeting_service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant
import java.util.stream.Stream

@Service
class GreetingService {

    /**
     * Returns a Mono of GreetingResponse
     */
    fun greetOnce(request : GreetingRequest) = Mono
            .just(GreetingResponse(greetMessage(request)))

    /**
     * Returns a infinite stream of GreetingResponses
     */
    fun greetMany (request:GreetingRequest) = Flux
            .fromStream(Stream.generate { GreetingResponse(greetMessage(request)) })
            .delayElements(Duration.ofSeconds(1))

    /**
     * Returns a formatted greeting message.
     */
    protected fun greetMessage(request:GreetingRequest) =
            "Hello ${request.name} @ ${Instant.now()} !"

}
