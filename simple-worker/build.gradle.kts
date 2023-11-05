dependencies {
    implementation("io.camunda:zeebe-client-java:8.2.13")
    // Logging
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")
//    // Use the Kotlin test library.
//    testImplementation("org.jetbrains.kotlin:kotlin-test")
//    // Use the Kotlin JUnit integration.
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    // Define the main class for the application.
    mainClass.set("com.antoinecampbell.camunda.platform.AppKt")
}
