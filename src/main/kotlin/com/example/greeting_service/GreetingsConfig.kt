package com.example.greeting_service

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.reactive.function.server.router

@Configuration
class GreetingsConfig (private val greetingsHandler: GreetingsHandler)  {

    /**
     * This is the router function. In kotlin we can either write the
     * router in a Java way
     *
     *       return route()
     *            .GET("/greetings/{name}" , ::greetingsHandler )
     *            .GET("/greetings" , ::getGreetingsHandler
     *            .build()
     *  or in a more idiomatic kotlin way.
     * https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/kdoc-api/spring-framework/org.springframework.web.reactive.function.server/-router-function-dsl/
     */
    @Bean
    fun routes() = router {

        GET("/greeting" , greetingsHandler::handleGreeting )
        GET("/greetings", greetingsHandler::handleGreetings)
    }

     @Bean
     fun authentication() : MapReactiveUserDetailsService = MapReactiveUserDetailsService(
                 User
                     .withDefaultPasswordEncoder()
                     .username("user")
                     .password("password")
                     .roles("USER")
                     .build())

     @Bean
     fun authorization(http: ServerHttpSecurity): SecurityWebFilterChain = http
             .csrf()
             .disable()
             .httpBasic(Customizer.withDefaults())
             .authorizeExchange {
                 it.pathMatchers("/greeting*").authenticated()
                         .anyExchange()
                         .permitAll() }
             .build()
}