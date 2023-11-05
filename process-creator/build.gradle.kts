dependencies {
    implementation("io.camunda:zeebe-client-java:8.2.13")
    testImplementation("io.camunda:zeebe-process-test-extension:8.2.13")
}

application {
    // Define the main class for the application.
    mainClass.set("com.antoinecampbell.camunda.platform.AppKt")
}

