plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

allprojects {
    group = "com.antoinecampbell.camunda.platform"
    apply(plugin = "kotlin")
    apply(plugin = "application")

    repositories {
        mavenCentral()
    }

    dependencies {
        // Align versions of all Kotlin components
        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

        testImplementation(platform("org.junit:junit-bom:5.9.2"))
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    tasks.test {
        useJUnitPlatform()
    }

    kotlin {
        jvmToolchain(21)
    }
}
