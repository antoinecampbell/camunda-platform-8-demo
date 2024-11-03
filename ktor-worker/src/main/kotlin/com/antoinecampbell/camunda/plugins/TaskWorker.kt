package com.antoinecampbell.camunda.plugins

import io.camunda.zeebe.client.ZeebeClient
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.Application
import io.ktor.server.application.createApplicationPlugin
import io.ktor.server.application.install
import java.time.Duration
import kotlin.math.max
import kotlin.random.Random

private val logger = KotlinLogging.logger {}

fun Application.configureTaskWorker() {
    install(createApplicationPlugin(name = "TaskWorker") {
        // Create client
        val zeebeClient = ZeebeClient.newClientBuilder()
            .numJobWorkerExecutionThreads(1)
            .build()

        // Send request to verify connection
        zeebeClient.newTopologyRequest().send().join()
        logger.info { "Connection successful" }

        // Create worker
        zeebeClient.newWorker()
            .jobType("task")
            .handler { client, job ->
                logger.info { job.toString() }
                val randomNumber = Random.nextInt(10)
                logger.info { "The random number is: $randomNumber" }

                // Complete job and add random number to process variables
                if (randomNumber == 3) {
                    client.newFailCommand(job.key)
                        .retries(max(0, job.retries - 1))
                        .errorMessage("The random number was 3 🤷🏾")
                        .send()
                        .join()
                } else {
                    client.newCompleteCommand(job.key)
                        .variables(mapOf("randomNumber" to randomNumber))
                        .send()
                        .join()
                }
            }
            .timeout(Duration.ofSeconds(30))
            .open()
    })
}

