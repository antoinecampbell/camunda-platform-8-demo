package com.antoinecampbell.camunda.platform

import io.camunda.zeebe.client.ZeebeClient
import java.time.Duration
import kotlin.random.Random

fun main() {
    // Create client
    val zeebeClient = ZeebeClient.newClientBuilder()
        .usePlaintext()
        .build()

    // Send request to verify connection
    zeebeClient.newTopologyRequest().send().join()
    println("Connection successful")

    // Create worker
    zeebeClient.newWorker()
        .jobType("task")
        .handler { client, job ->
            println(job)
            val randomNumber = Random.nextInt(10)
            println("The random number is: $randomNumber")

            // Complete job and add random number to process variables
            client.newCompleteCommand(job.key)
                .variables(mapOf("randomNumber" to randomNumber))
                .send()
                .join()
        }
        .timeout(Duration.ofSeconds(30))
        .open()
}