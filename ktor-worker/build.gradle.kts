val ktorVersion: String by project
val zeebeVersion: String by project

plugins {
    id("io.ktor.plugin")
}

application {
    mainClass.set("com.antoinecampbell.camunda.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    val args = mutableListOf("-Dio.ktor.development=$isDevelopment")
    if (isDevelopment) {
        args += "-Dlogback.configurationFile=logback-local.xml"
    }
    applicationDefaultJvmArgs = args
}

dependencies {
    // Ktor
    implementation(platform("io.ktor:ktor-bom:$ktorVersion"))
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    // Ktor plugins
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-jackson-jvm")
    implementation("io.ktor:ktor-server-metrics-micrometer-jvm")
    implementation("io.micrometer:micrometer-registry-prometheus:1.13.0")
    // Zeebe
    implementation(platform("io.camunda:zeebe-bom:$zeebeVersion"))
    implementation("io.camunda:zeebe-client-java")
    // Logging
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.9")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("net.logstash.logback:logstash-logback-encoder:7.4")
}
