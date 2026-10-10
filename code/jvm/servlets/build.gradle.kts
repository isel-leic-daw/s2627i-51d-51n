plugins {
    kotlin("jvm") version "2.2.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

group = "pt.isel.daw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Jetty
    implementation("org.eclipse.jetty:jetty-server:12.1.10")
    implementation("org.eclipse.jetty.ee10:jetty-ee10-servlet:12.1.1")

    // Tomcat
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.11")

    // For logging purposes
    implementation("org.slf4j:slf4j-api:2.0.17")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.17")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}