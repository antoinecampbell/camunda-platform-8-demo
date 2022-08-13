package com.antoinecampbell.camunda.platform

import io.camunda.zeebe.client.ZeebeClient

fun main() {
    // Create client
    val zeebeClient = ZeebeClient.newClientBuilder()
        .usePlaintext()
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
    println("Diagram deployed")

    // Start test processes
    (0..10).forEach { _ ->
        startProcess(zeebeClient, "test")
    }
}

fun startProcess(zeebeClient: ZeebeClient, processId: String) {
    zeebeClient.newCreateInstanceCommand()
        .bpmnProcessId(processId)
        .latestVersion()
        .send()
    println("Process started")
}