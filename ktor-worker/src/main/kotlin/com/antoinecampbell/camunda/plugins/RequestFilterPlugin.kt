package com.antoinecampbell.camunda.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.install
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.plugins.origin

val requestFilterPlugin = createApplicationPlugin(
    name = "RequestFilter",
) {
    val paths = listOf(PATH_METRICS)
    onCall { call ->
        call.request.origin.apply {
            if (serverPort == 9090 && paths.none { uri.startsWith(it) }) throw NotFoundException()
            if (serverPort != 9090 && paths.any { uri.startsWith(it) }) throw NotFoundException()
        }
    }
}

fun Application.configureRequestFilter() {
    install(requestFilterPlugin)
}
