package com.example.greeting_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity

@SpringBootApplication
class GreetingServiceApplication

fun main(args: Array<String>) {
    runApplication<GreetingServiceApplication>(*args)
}

data class GreetingRequest(val name:String)
data class GreetingResponse(val message:String)




