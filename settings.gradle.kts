/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 *
 * Detailed information about configuring a multi-project build in Gradle can be found
 * in the user manual at https://docs.gradle.org/7.5.1/userguide/multi_project_builds.html
 */

pluginManagement {
    val kotlinVersion: String by settings
    val ktorVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        id("io.ktor.plugin") version ktorVersion
    }
}

rootProject.name = "camunda-platform"
include("simple-worker")
include("process-creator")
include("ktor-worker")
