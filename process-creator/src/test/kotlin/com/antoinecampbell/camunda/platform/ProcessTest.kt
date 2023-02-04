package com.antoinecampbell.camunda.platform

import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.client.api.response.DeploymentEvent
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent
import io.camunda.zeebe.process.test.api.ZeebeTestEngine
import io.camunda.zeebe.process.test.assertions.BpmnAssert
import io.camunda.zeebe.process.test.extension.ZeebeProcessTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Duration

@ZeebeProcessTest
class ProcessTesT {

    // Variables injected by ZeebeProcessTest extensions
    private lateinit var engine: ZeebeTestEngine
    private lateinit var client: ZeebeClient
    private lateinit var processEvent: ProcessInstanceEvent

    @BeforeEach
    fun setUp() {
        // Deploy diagrams
        val event: DeploymentEvent = client.newDeployResourceCommand()
            .addResourceFromClasspath("test.bpmn")
            .addResourceFromClasspath("test.dmn")
            .send()
            .join()
        BpmnAssert.assertThat(event)
    }

    @Test
    fun `should test happy path`() {
        // Start process
        startProcess()
        // Expect process to be waiting at the first task
        BpmnAssert.assertThat(processEvent)
            .isStarted
            .isWaitingAtElements("task1")
        // Complete the first task
        completeServiceTask(variables = mapOf("randomNumber" to 1))
        // Ensure the process is complete
        BpmnAssert.assertThat(processEvent)
            .isCompleted
    }

    @Test
    fun `should require an additional task`() {
        // Start process
        startProcess()
        // Expect process to be waiting at the first task
        BpmnAssert.assertThat(processEvent)
            .isStarted
            .isWaitingAtElements("task1")
        // Complete the first task with a value that requires an additional task
        completeServiceTask(variables = mapOf("randomNumber" to 6))
        // Expect process to be waiting at the second task
        BpmnAssert.assertThat(processEvent)
            .isStarted
            .isWaitingAtElements("task2")
        // Complete the second task
        completeServiceTask(variables = mapOf("randomNumber" to 6))
        // Ensure the process is complete
        BpmnAssert.assertThat(processEvent)
            .isCompleted
    }

    @Test
    fun `should cause an incident`() {
        // Start process
        startProcess()
        // Expect process to be waiting at the first task
        BpmnAssert.assertThat(processEvent)
            .isStarted
            .isWaitingAtElements("task1")
        // Fail the first task with a value causing an incident
        failServiceTask()
        // Expect process to be waiting at the second task
        BpmnAssert.assertThat(processEvent)
            .hasAnyIncidents()
            .isWaitingAtElements("task1")
    }

    private fun startProcess() {
        processEvent = client.newCreateInstanceCommand()
            .bpmnProcessId("test")
            .latestVersion()
            .send()
            .join()
        waitForIdle()
    }

    private fun getJob(name: String): ActivatedJob {
        return client.newActivateJobsCommand()
            .jobType(name)
            .maxJobsToActivate(1)
            .fetchVariables()
            .send()
            .join()
            .jobs
            .first()
    }

    private fun completeServiceTask(name: String = "task", variables: Map<String, Any> = emptyMap()) {
        val job: ActivatedJob = getJob(name)

        client.newCompleteCommand(job.key)
            .variables(variables)
            .send()
            .join()
        waitForIdle()
    }

    private fun failServiceTask(name: String = "task") {
        val job: ActivatedJob = getJob(name)

        client.newFailCommand(job.key)
            .retries(0)
            .errorMessage("Error")
            .send()
            .join()
        waitForIdle()
    }

    private fun waitForIdle(seconds: Long = 1) {
        engine.waitForIdleState(Duration.ofSeconds(seconds))
    }
}
