val zeebeVersion: String by project

dependencies {
    // Zeebe
    implementation(platform("io.camunda:zeebe-bom:$zeebeVersion"))
    implementation("io.camunda:zeebe-client-java")
    // Logging
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.9")
    implementation("ch.qos.logback:logback-classic:1.5.6")
//    // Use the Kotlin test library.
//    testImplementation("org.jetbrains.kotlin:kotlin-test")
//    // Use the Kotlin JUnit integration.
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    // Define the main class for the application.
    mainClass.set("com.antoinecampbell.camunda.platform.AppKt")
}
