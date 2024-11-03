package com.antoinecampbell.camunda.platform

import io.camunda.zeebe.client.ZeebeClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.UUID

fun main() {
    // Create client
    val zeebeClient = ZeebeClient.newClientBuilder()
        .build()

    // Send request to verify connection
    zeebeClient.newTopologyRequest().send().join()
    println("Connection successful")

    // Deploy diagrams
    zeebeClient.newDeployResourceCommand()
        .addResourceFromClasspath("test.bpmn")
        .addResourceFromClasspath("test.dmn")
        .send()
        .join()
    println("Diagrams deployed")

    // Start test processes
    runBlocking(Dispatchers.IO.limitedParallelism(2)) {
        (1..1000).map { _ ->
            async { startProcess(zeebeClient, "test") }
        }
    }
}

fun startProcess(zeebeClient: ZeebeClient, processId: String) {
    zeebeClient.newCreateInstanceCommand()
        .bpmnProcessId(processId)
        .latestVersion()
        .variables(mapOf("businessKey" to UUID.randomUUID().toString()))
        .send()
        .join()
    println("Process started on thread: ${Thread.currentThread().name}")
}
