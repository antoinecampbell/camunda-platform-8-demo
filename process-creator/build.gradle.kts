val zeebeVersion: String by project
val kotlinxVersion: String by project

dependencies {
    // Zeebe
    implementation(platform("io.camunda:zeebe-bom:$zeebeVersion"))
    implementation("io.camunda:zeebe-client-java")
    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$kotlinxVersion")

    testImplementation("io.camunda:zeebe-process-test-extension:$zeebeVersion")
}

application {
    // Define the main class for the application.
    mainClass.set("com.antoinecampbell.camunda.platform.AppKt")
}
