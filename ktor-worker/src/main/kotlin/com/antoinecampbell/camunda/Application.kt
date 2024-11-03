package com.antoinecampbell.camunda

import com.antoinecampbell.camunda.plugins.configureMonitoring
import com.antoinecampbell.camunda.plugins.configureRequestFilter
import com.antoinecampbell.camunda.plugins.configureRouting
import com.antoinecampbell.camunda.plugins.configureSerialization
import com.antoinecampbell.camunda.plugins.configureTaskWorker
import io.ktor.server.application.Application
import io.ktor.server.engine.applicationEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        Netty,
        environment = applicationEnvironment(),
        configure = {
            connector { port = 8080 }
            connector { port = 9090 }
        },
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureRouting()
    configureRequestFilter()
    configureTaskWorker()
}
